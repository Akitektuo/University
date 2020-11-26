create view LatestActiveUsers as
	select distinct [UserId] from [Logs] where [Timestamp] > '2020-11-17'

select * from LatestActiveUsers

create view CurrencyNamesGreaterThanReference as
	select [Name] from [Currencies]
	where (select avg([Value]) from [CurrencyHistory] where [CurrencyHistory].[CurrencyId] = [Currencies].[Id]) < 1

select * from [CurrencyHistory]
select * from [Currencies]
select * from CurrencyNamesGreaterThanReference

create view ProductNamesForEachCategory as
	select [Categories].[Name] as 'Category', [Products].[Name] as 'Product' from [Products]
	inner join [Categories] on [Products].[CategoryId] = [Categories].[Id]
	group by [Categories].[Name]
select * from ProductNamesForEachCategory