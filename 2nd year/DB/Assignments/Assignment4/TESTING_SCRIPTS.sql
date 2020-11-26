create procedure createTest
	@testName varchar(64)
as
begin
	if @testName in (select [Name] from [Tests])
	begin
		print 'There already is a test with the name ' + @testName
		return
	end
	insert into [Tests]([Name]) values (@testName)
end

exec createTest 'test0'
select * from [Tests]


create procedure trackTableToBeTested
	@tableName varchar(64)
as
begin 
	if not exists (select * from INFORMATION_SCHEMA.TABLES  where [TABLE_NAME] = @tableName)
	begin
		print('Table ' + @tableName + ' does not exist')
		return
	end

	if @tableName not in (select [Name] from [Tables])
	begin
		insert into [Tables]([Name]) values (@tableName)
	end else begin
		print('Table ' + @tableName + ' already added')
	end
end

exec trackTableToBeTested 'Categories'
exec trackTableToBeTested 'Products'
exec trackTableToBeTested 'CurrencyHistory'

select * from [Tables]


create procedure addConnectionBetweenTablesAndTests
	@tableName varchar(64),
	@testName varchar(64),
	@numberOfRows int,
	@position int
as 
begin 
	if @position <= 0
	begin 
		print 'Position must be greater than 0'
		return
	end

	if @numberOfRows <= 0
	begin 
		print 'Number of rows must be greater than 0'
		return
	end

	declare @testId int, @tableId int

	set @testId = (select [TestID] from [Tests] where [Name] = @testName)
	if @testId is null
	begin
		print 'There is no test with the name of ' + @testName
		return
	end
	
	SET @tableId = (select [TableID] from [Tables] where [Name] = @tableName)
	if @tableId is null
	begin
		print 'There is no table with the name of ' + @tableName
		return
	end

	if (select count(*) from [TestTables] where [TestId] = @testId and [TableId] = @tableId) > 0
	begin
		print 'There is already a connection between table with ID ' + @tableId + ' and test with ID ' + @testId
		return
	end

	insert into [TestTables]([TestID], [TableID], [NoOfRows], [Position]) values
		(@testId, @tableId, @numberOfRows, @position)
end

exec addConnectionBetweenTablesAndTests 'Categories', 'test0', 200, 1
select * from [TestTables]
delete from [TestTables]


create procedure trackViewToBeTested
	@viewName varchar(64)
as
begin
	if not exists (select * from INFORMATION_SCHEMA.VIEWS  where [TABLE_NAME] = @viewName)
	begin
		print('View ' + @viewName + ' does not exist')
		return
	end

	if @viewName not in (select [Name] from [Views])
	begin
		insert into [Views]([Name]) values (@viewName)
	end else begin
		print('View ' + @viewName + ' already added')
	end
end

exec trackViewToBeTested 'CurrencyNamesGreaterThanReference'
exec trackViewToBeTested 'LatestActiveUsers'
exec trackViewToBeTested 'ProductNamesForEachCategory'

select * from [Views]


create procedure addConnectionBetweenViewsAndTests
	@viewName varchar(64),
	@testName varchar(64)
as 
begin 
	declare @testId int, @viewId int

	set @testId = (select [TestID] FROM [Tests] where [Name] = @testName)
	if @testId is null
	begin
		print 'There is no test with the name of ' + @testName
		return
	end
	
	set @viewId = (select [ViewID] from [Views] where [Name] = @viewName)
	if @viewId is null
	begin
		print 'There is no view with the name of ' + @viewName
		return
	end

	if (select count(*) from [TestViews] where [TestId] = @testId and [ViewId] = @viewId) > 0
	begin
		print 'There is already a connection between view with ID ' + @viewId + ' and test with ID ' + @testId
		return
	end

	insert into [TestViews]([TestID], [ViewID]) values
		(@testId, @viewId)
end

create procedure runTest @testName varchar(64)
as
begin
	declare @testId int
	set @testId = (select [TestID] from [Tests] where [Name] = @testName)

	if @testId is null
	begin
		print 'There is no test with the name of ' + @testName
		return
	end

	declare TablesCursor cursor scroll for
		select [Tables].[TableID], [Tables].[Name], [TestTables].[NoOfRows]
		from [TestTables] inner join [Tables] on [TestTables].[TableID] = [Tables].[TableID]
		where [TestTables].[TestID] = @testId
		order by [TestTables].[Position]
	
	declare ViewsCursor cursor for
		select [Views].[ViewID], [Views].[Name]
		from [Views] inner join [TestViews] on [Views].[ViewID] = [TestViews].[ViewId]
		where [TestViews].[TestID] = @testId

	set nocount on

	insert into [TestRuns]([Description]) values
		('Test results for ' + @testName)

	declare @testRunId int
	set @testRunId = convert(int, (select [LAST_VALUE] from sys.identity_columns where [name] = 'TestRunID'))

	declare @globalStartTime datetime
	set @globalStartTime = sysdatetime()
	
	declare @table varchar(64),
		@numberOfRows int,
		@tableId int

	open TablesCursor
	fetch first from TablesCursor
	into @tableId, @table, @numberOfRows
	while @@fetch_status = 0
	begin
		exec('delete from ' + @table)
		fetch next from TablesCursor
		into @tableId, @table, @numberOfRows	
	end

	close TablesCursor
	open TablesCursor

	declare @command varchar(512),
		@localStartTime datetime,
		@localEndTime datetime

	fetch last from TablesCursor
	into @tableId, @table, @numberOfRows
	while @@fetch_status = 0
	begin
		set @command = 'populateTable ''' + @table + ''', ' + convert(varchar(10), @numberOfRows)

		set @localStartTime = sysdatetime()
		exec (@command)
		set @localEndTime = sysdatetime()

		insert into [TestRunTables]([TestRunID], [TableID], [StartAt], [EndAt]) values
			(@testRunId, @tableId, @localStartTime, @localEndTime)
		
		fetch prior from TablesCursor
		into @tableId, @table, @numberOfRows
	end

	declare @viewId int,
		@view varchar(64)

	open ViewsCursor
	fetch ViewsCursor
	into @viewId, @view
	
	declare @position int,
		@globalEndTime datetime

	while @@fetch_status = 0
	begin
		set @command = 'select * from ' + @view

		set @localStartTime = sysdatetime()
		exec (@command)
		set @localEndTime = sysdatetime()

		insert into [TestRunViews]([TestRunID], [ViewID], [StartAt], [EndAt]) values
			(@testRunId, @viewId, @localStartTime, @localEndTime)
		
		fetch ViewsCursor
		into @viewId, @view
	end
	set @globalEndTime = sysdatetime()

	update [TestRuns]
	set [StartAt] = @globalStartTime, [EndAt] = @globalEndTime
	where [TestRunID] = @testRunId

	close TablesCursor
	close ViewsCursor
	deallocate ViewsCursor
	deallocate TablesCursor

	set nocount off
end

create procedure getResultsForTest @testName varchar(64)
as
begin
	select * from [TestRuns]
	where [Description] like '%' + @testName + '%'

	select [TestRunTables].[TestRunID], [Tables].[Name], [TestRunTables].[StartAt], [TestRunTables].[EndAt]
	from TestRunTables inner join [Tables] on [TestRunTables].[TableID] = [Tables].[TableID]
	where [TestRunTables].[TestRunID] in (
		select [TestRunID] from [TestRuns]
		where [Description] like '%' + @testName + '%'
	)
	order by [StartAt]
	
	select [TestRunViews].[TestRunID], [Views].[Name], [TestRunViews].[StartAt], [TestRunViews].[EndAt]
	from [TestRunViews] inner join [Views] on [TestRunViews].[ViewID] = [Views].[ViewID]
	where [TestRunViews].[TestRunID] in (
		select [TestRunID] from [TestRuns]
		where [Description] like '%' + @testName + '%'
	)
	order by [StartAt]
end

exec createTest 'test1'

exec addConnectionBetweenViewsAndTests 'CurrencyNamesGreaterThanReference', 'test1'
exec addConnectionBetweenViewsAndTests 'LatestActiveUsers', 'test1'
exec addConnectionBetweenViewsAndTests 'ProductNamesForEachCategory', 'test1'

select * from [TestViews]
select * from [Views]

exec addConnectionBetweenTablesAndTests 'Categories', 'test1', 5000, 1
exec addConnectionBetweenTablesAndTests 'Products', 'test1', 5000, 2
exec addConnectionBetweenTablesAndTests 'CurrencyHistory', 'test1', 2000, 3

select * from [TestTables]
select * from [Tables]


select * from [Categories]
select * from [Products]
select * from [CurrencyHistory]

exec runTest 'test1'

delete from [TestRuns]
delete from [TestRunTables]
delete from [TestRunViews]


exec createTest 'test2'

exec addConnectionBetweenTablesAndTests 'Categories', 'test2', 1000, 1
exec addConnectionBetweenTablesAndTests 'Products', 'test2', 1000, 2
exec addConnectionBetweenTablesAndTests 'CurrencyHistory', 'test2', 500, 3

exec addConnectionBetweenViewsAndTests 'ProductNamesForEachCategory', 'test2'

exec runTest 'test2'

exec getResultsForTest'test2'