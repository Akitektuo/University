--SELECT * FROM Products
--sp_helpindex Products

--SELECT @@SPID

--SELECT @@TRANCOUNT

BEGIN TRAN

UPDATE Products
SET Price = 100
WHERE Id = 1

UPDATE Products
SET Price = 200
WHERE Id = 2

COMMIT TRAN