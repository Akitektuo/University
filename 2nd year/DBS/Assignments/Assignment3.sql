CREATE OR ALTER PROCEDURE addUserAndListAllOrNothing @userId INT, @listId INT, @name VARCHAR(100), @email VARCHAR(100), @listName VARCHAR(100)
AS
	BEGIN TRAN
	BEGIN TRY
		INSERT INTO [Users] VALUES (@userId, @name, @email)
		INSERT INTO [Lists] VALUES (@listId, @listName)
		INSERT INTO [UserLists] VALUES (@userId, @listId, 1, 1)
			
		COMMIT TRAN
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
	END CATCH
GO

CREATE OR ALTER PROCEDURE addUserAndListOnlySuccess @userId INT, @listId INT, @name VARCHAR(100), @email VARCHAR(100), @listName VARCHAR(100)
AS
	BEGIN TRAN

	INSERT INTO [Users] VALUES (@userId, @name, @email)
	INSERT INTO [Lists] VALUES (@listId, @listName)
	INSERT INTO [UserLists] VALUES (@userId, @listId, 1, 1)

	COMMIT TRAN
GO

SELECT * FROM [Users]
SELECT * FROM [Lists]
SELECT * FROM [UserLists]

EXEC addUserAndListAllOrNothing 7, 3, 'Test', 'test@test.com', 'Testing'
EXEC addUserAndListOnlySuccess 7, 3, 'Test', 'test@test.com', 'Testing'

DELETE FROM [UserLists] WHERE [UserId] = 7
DELETE FROM [Users] WHERE [Id] = 7