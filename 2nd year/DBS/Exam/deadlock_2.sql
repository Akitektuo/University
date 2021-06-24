SET DEADLOCK_PRIORITY HIGH

BEGIN TRAN
UPDATE Tasks
SET [Status] = 'Closed'
WHERE [Id] = 2

UPDATE Tasks
SET [Status] = 'Closed'
WHERE [Id] = 1
COMMIT TRAN