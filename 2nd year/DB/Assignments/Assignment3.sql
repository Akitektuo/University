drop procedure GetUserNames

create procedure GetUserNames(@haveIdAtLeast int)
as
	select [Name] from [Users] where [Users].[Id] >= @haveIdAtLeast
go

exec GetUserNames 1