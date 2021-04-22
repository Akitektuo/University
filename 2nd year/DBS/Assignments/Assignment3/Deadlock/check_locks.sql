SELECT resource_type, request_mode, request_type, request_status, request_session_id FROM SYS.dm_tran_locks
WHERE request_owner_type = 'TRANSACTION'