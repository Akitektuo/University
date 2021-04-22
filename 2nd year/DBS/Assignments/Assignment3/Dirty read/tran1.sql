BEGIN TRAN
UPDATE [Logs]
SET [Message] = 'I am dirty'  
WHERE [Id] = 4

ROLLBACK