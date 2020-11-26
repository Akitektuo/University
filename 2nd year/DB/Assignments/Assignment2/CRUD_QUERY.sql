USE [ExpenseTracker]
GO

INSERT INTO [dbo].[Currencies]
           ([Id], [Name])
     VALUES
           (1, 'EUR'),
           (2, 'RON'),
           (3, 'USD')
GO

INSERT INTO [dbo].[CurrencyHistory]
           ([Date], [CurrencyId], [Value])
     VALUES
           ('2020-10-13', 2, 4.87),
		   ('2020-10-13', 3, 1.18)
GO

INSERT INTO [dbo].[Users]
           ([Id], [Name], [Email])
     VALUES
           (1, 'Alex Copindean', 'alexcopindean@yahoo.com'),
		   (2, 'Vasile Pop', 'vasilepop91@gmail.com')
GO

INSERT INTO [dbo].[Categories]
           ([Id], [Name])
     VALUES
           (1, 'Meal'),
           (2, 'Sweets'),
           (3, 'Drink')
GO

INSERT INTO [dbo].[Products]
           ([Id]
           ,[CategoryId]
           ,[CurrencyId]
           ,[Name]
           ,[Price])
     VALUES
		   (1, 1, 2, 'Vegetables soup', 7.5),
           (2, 2, 2, 'Milka chocholate with Oreo', 4.2)
GO

INSERT INTO [dbo].[Lists]
           ([Id], [Name])
     VALUES
           (1, 'Launch, 13 Oct 2020'),
           (2, 'Some list')
GO

INSERT INTO [dbo].[ListProducts]
           ([ListId], [ProductId], [Quantity], [Timestamp])
     VALUES
           (1, 1, 1, '2020-10-13 14:01:56'),
		   (1, 2, 2, '2020-10-13 14:12:34')
GO

INSERT INTO [dbo].[UserLists]
           ([UserId]
           ,[ListId]
           ,[Owner]
           ,[Write])
     VALUES
           (1, 1, 1, 1),
           (2, 2, 1, 1)
GO

INSERT INTO [dbo].[Logs]
           ([Id], [UserId], [Message], [Timestamp])
     VALUES
           (1, 1, 'Alex Copindean created list Launch, 13 Oct 2020', '2020-10-13 16:07:53')
GO

insert into [dbo].[UserConnections]
	([UserAId], [UserBId]) values (1, 2)
go

UPDATE [dbo].[Products]
   SET [Name] = 'Large Milka chocholate with milk and Oreo'
      ,[Price] = 11.4
 WHERE [Id] = 2
GO

update [dbo].[Users]
	set [Name] = 'Alexandru Copindean',
		[Email] = 'alexcopindean@gmail.com'
where [Id] = 1
go

update [dbo].[CurrencyHistory]
	set [Date] = '2020-10-12' where [Date] >= '2020-10-12'

delete from [dbo].[Logs] where [Message] like '%Alex%'

delete from [dbo].[UserConnections] where [UserAId] between 5 and 10

select * from [dbo].[Logs]

select [UserAId] as [UserId] from [dbo].[UserConnections]
union
select [UserBId] as [UserId] from [dbo].[UserConnections]
order by [UserId]

select [Id] from [dbo].[Lists]
union all
select [Id] from [dbo].[Products] where [CurrencyId] = 1 or [CurrencyId] = 2
order by [Id]

select [UserId] from [dbo].[UserLists]
intersect
select [UserId] from [dbo].[Logs]

select [UserId] from [dbo].[UserLists]
intersect
select [Id] as [UserId] from [dbo].[Users] where [Id] in (1, 3, 5, 7, 9)

select [Id] as [ProductId] from [dbo].[Products]
except
select [ProductId] from [dbo].[ListProducts]
order by [ProductId]

select [Id] as [UserId] from [dbo].[Users]
except
select [UserId] from [dbo].[Logs] where [UserId] not in (2, 5, 10)

select
	[Products].[Name] as [Product],
	[Categories].[Name] as [Category],
	[Products].[Price] * 2 as [DoubledPrice],
	[Currencies].[Name] as [Currency]
from (([Products]
inner join [Categories] on [Products].[CategoryId] = [Categories].[Id])
inner join [Currencies] on [Products].[CurrencyId] = [Currencies].[Id]);

select
	[Lists].[Name] as [ListName],
	[ListProducts].[ProductId],
	[UserLists].[UserId]
from (([Lists]
inner join [ListProducts] on [ListProducts].[ListId] = [Lists].[Id])
inner join [UserLists] on [UserLists].[ListId] = [Lists].[Id]);

select [ListProducts].[Quantity], [ListProducts].[Timestamp], [Products].[Name]
from [ListProducts]
right join [Products] on [ListProducts].[ProductId] = [Products].Id

select [ListProducts].[Timestamp], [Lists].[Name]
from [ListProducts]
left join [Lists] on [ListProducts].[ListId] = [Lists].[Id]

select
	[Users].[Name] as [UserName],
	[Users].[Email] as [UserEmail],
	[UserLists].[Owner] as [IsOwner],
	[UserLists].[Write] as [CanWrite]
from [UserLists]
full outer join [Users] on [UserLists].[UserId] = [Users].[Id]

select * from [Currencies]
where [Id] in (
	select [CurrencyId] from [CurrencyHistory] where [Value] < 2
)

select * from [Users]
where [Id] in (
	select [UserId] from [UserLists]
)

select DISTINCT [Name], [Price] from [Products]
where exists (
	select [ProductId] from [ListProducts]
	where [ListProducts].[ProductId] = [Products].[Id] and [Quantity] > 1
)

select [Name], [Email] from [Users]
where exists (
	select * from [UserConnections]
	where
		[UserConnections].[UserAId] = [Users].[Id] or
		[UserConnections].[UserBId] = [Users].[Id]
)

select top(5) * from [UserConnections]

select DISTINCT [Products].[Price] from (
	select avg([Products].[Price]) as [AveragePrice] from [Products]
) as [Prices], [Products] where [Products].[Price] > [Prices].[AveragePrice]

select count(*) as [NumberOfHighestQuantityProducts] from (
	select max([ListProducts].[Quantity] * 10) as [Maximum] from [ListProducts]
) as [Quantity], [ListProducts] where [ListProducts].[Quantity] = [Quantity].[Maximum]

select [Id], [Name] from [Currencies]
group by [Name], [Id]

select top(10) [Id], [Name] from [Lists]
group by [Name], [Id]
having lower([Name]) like '%list%'

/* Product IDs used more than once */
select [ProductId], sum([Quantity]) from [ListProducts]
group by [ProductId]
having sum([Quantity]) > 2

/* The average price per categories that have the name composed from at least 5 characters */
select [CategoryId], avg(Price) from [Products]
group by [CategoryId]
having [CategoryId] = (select Id from [Categories] where len([Categories].[Name]) > 5)

select [Name], [Price] from [Products]
where [Price] = any (select [Price] from [Products] where [Price] < 10)

select [Name], [Email] from [Users]
where [Id] = any (select [UserId] from [Logs] where [Id] = [UserId])

select [ProductId], [Quantity] from [ListProducts]
where [Quantity] = all (select [Price] from [Products] where [Price] < 5 and [ProductId] = [Price])

select DISTINCT [Name] from [Lists]
where [Id] = all (select [ListId] from [UserLists] where [Owner] = 1)

select [Name], [Price] from [Products]
where (select count([Price]) from [Products] where [Price] < 10) > 0

select [Name], [Email] from [Users]
where (select count([UserId]) from [Logs] where [Id] = [UserId]) > 0

select [ProductId], [Quantity] from [ListProducts]
where [Quantity] in (select [Price] from [Products] where [Price] < 5 and [ProductId] = [Price])

select [Name] from [Lists]
where [Id] not in (select [ListId] from [UserLists] where [Owner] = 0)