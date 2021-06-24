BEGIN TRAN
UPDATE Tasks
SET [Status] = 'In progress'
WHERE [Id] = 1

UPDATE Tasks
SET [Status] = 'In progress'
WHERE [Id] = 2
COMMIT TRAN