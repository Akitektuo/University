create procedure seedCategoriesTable @entry int
as
begin
	insert into [Categories]([Id], [Name]) values
	(
		@entry,
		'Name' + convert(varchar(10), @entry)
	)
end

create procedure seedProductsTable @entry int
as
begin
	insert into [Products]([Id], [CategoryId], [CurrencyId], [Name], [Price]) values
	(
		@entry,
		(select top(1) [Id] from [Categories] order by rand()),
		(select top(1) [Id] from [Currencies] order by rand()),
		'Name' + convert(varchar(10), @entry),
		rand() * (100 - 1) + 1
	)
end

create procedure seedCurrencyHistoryTable @entry int
as
begin
	begin try
		insert into [CurrencyHistory]([CurrencyId], [Date], [Value]) values
		(
			@entry % 3 + 1,
			dateadd(day, (abs(checksum(newid())) % 65530), 0),
			round(rand(checksum(newid())) * (100), 2)
		)
	end try  
	begin catch
		exec seedCurrencyHistoryTable @entry
	end catch 
	
end

drop procedure seedCurrencyHistoryTable

create procedure populateTable
	@tableName varchar(32),
	@numberOfRows int
as
begin
	declare @rowNumber int, @command varchar(512)
	set @rowNumber = 100
	set @numberOfRows = @numberOfRows + 100

	while @rowNumber < @numberOfRows
	begin
		set @command = 'seed' + @tableName + 'Table ' + convert(varchar(10), @rowNumber)
		exec(@command)
		set @rowNumber = @rowNumber + 1
	end
end

exec populateTable 'Categories', 10000

select * from [Categories]

delete from [Categories] where [Id] >= 100