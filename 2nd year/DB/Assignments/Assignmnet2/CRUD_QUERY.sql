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
           ([Id], [Date], [CurrencyId], [Value])
     VALUES
           (1, '2020-10-13', 2, 4.87),
		   (2, '2020-10-13', 3, 1.18)
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
           ([Id], [Message], [Timestamp])
     VALUES
           (1, 'Alex Copindean created list Launch, 13 Oct 2020', '2020-10-13 16:07:53')
GO

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

select * from [dbo].[Logs]