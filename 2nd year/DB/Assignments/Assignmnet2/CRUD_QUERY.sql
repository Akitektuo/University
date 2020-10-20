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
           (1, 'Launch, 13 Oct 2020')
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
           (1, 1, 1, 1)
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

select [UserId] from [dbo].[UserLists]
intersect
select [UserId] from [dbo].[Logs]

select [UserId] from [dbo].[UserLists]
intersect
select [Id] as [UserId] from [dbo].[Users] where [Id] in (1, 3, 5, 7, 9)

select [Id] as [ProductId] from [dbo].[Products]
except
select [ProductId] from [dbo].[ListProducts]

select [Id] as [UserId] from [dbo].[Users]
except
select [UserId] from [dbo].[Logs] where [UserId] not in (2, 5, 10)

select
	[Products].[Name] as [Product],
	[Categories].[Name] as [Category],
	[Products].[Price],
	[Currencies].[Name] as [Currency]
from (([Products]
inner join [Categories] on [Products].[CategoryId] = [Categories].[Id])
inner join [Currencies] on [Products].[CurrencyId] = [Currencies].[Id]);

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

select [Name], [Price] from [Products]
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