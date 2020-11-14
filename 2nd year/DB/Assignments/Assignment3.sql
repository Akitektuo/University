drop procedure AlterUserListsOwnerColumnType go
drop procedure RevertAlterUserListsOwnerColumnType go
drop procedure AlterUserListsOwnerColumn go
drop procedure RevertAlterUserListsOwnerColumn go
drop procedure AddDefaultToUserLists go
drop procedure RevertAddDefaultToUserLists go
drop procedure AddPrimaryKeyToUsers go
drop procedure RevertAddPrimaryKeyToUsers go
drop procedure AddCandidateKeyToLists go
drop procedure RevertAddCandidateKeyToLists go
drop procedure AddForeignKeyToLogs go
drop procedure RevertAddForeignKeyToLogs go
drop procedure CreateMessagesTable go
drop procedure RevertCreateMessagesTable go


create procedure AlterUserListsOwnerColumnType as
	alter table [UserLists]
	alter column [Owner] tinyint
go

create procedure RevertAlterUserListsOwnerColumnType as
	alter table [UserLists]
	alter column [Owner] bit
go

create procedure AlterUserListsOwnerColumn as
	alter table [UserLists]
	add [ConnectionDate] datetime
go

create procedure RevertAlterUserListsOwnerColumn as
	alter table [UserLists]
	drop column [ConnectionDate]
go

create procedure AddDefaultToUserLists as
	alter table [UserLists]
	add constraint [OwnerDefault] default 0 for [Owner]
go

create procedure RevertAddDefaultToUserLists as
	alter table [UserLists]
	drop constraint [OwnerDefault]
go

create procedure AddPrimaryKeyToUsers as
	alter table [Users]
	add constraint [PK_Users] primary key ([Id], [Email])
go

create procedure RevertAddPrimaryKeyToUsers as
	alter table [Users]
	drop constraint [PK_Users]
go

create procedure AddCandidateKeyToLists as
	alter table [Lists]
	add constraint [CK_Lists] unique ([Name])
go

create procedure RevertAddCandidateKeyToLists as
	alter table [Lists]
	drop constraint [CK_Lists]
go

create procedure AddForeignKeyToLogs as
	alter table [Logs]
	add constraint [FP_Logs] foreign key ([UserId]) references [Users]([Id])
go

create procedure RevertAddForeignKeyToLogs as
	alter table [Logs]
	drop constraint [FP_Logs]
go

create procedure CreateMessagesTable as
	create table [Messages] (
		[Id] int primary key,
		[SenderId] int references Users(Id),
		[ReceiverId] int references Users(Id),
		[Text] varchar(1024)
	)
go

create procedure RevertCreateMessagesTable as
	drop table [Messages]
go

execute AlterUserListsOwnerColumnType
execute RevertAlterUserListsOwnerColumnType
execute AlterUserListsOwnerColumn
execute RevertAlterUserListsOwnerColumn
execute AddDefaultToUserLists
execute RevertAddDefaultToUserLists
execute AddPrimaryKeyToUsers
execute RevertAddPrimaryKeyToUsers
execute AddCandidateKeyToLists
execute RevertAddCandidateKeyToLists
execute AddForeignKeyToLogs
execute RevertAddForeignKeyToLogs
execute CreateMessagesTable
execute RevertCreateMessagesTable



create table [Versions] (
		[Version] int primary key
	)

insert into [Versions] ([Version]) values (0)

select top(1) [Version] from [Versions]



drop procedure UpdateDatabase go


create procedure UpdateDatabase (@wantedVersion tinyint) as
	if (@wantedVersion < 0 or @wantedVersion > 7)
		begin
			print 'The version has to be between 0 and 7'
			return
		end

	declare @currentVersion int

	select @currentVersion=[Version] from [Versions]

	if (@wantedVersion = @currentVersion)
		begin
			print concat('You are already to version ', @wantedVersion)
			return
		end

	if (@wantedVersion < @currentVersion)
		begin
			if (@currentVersion > 6 and @wantedVersion <= 6)
				execute RevertCreateMessagesTable
			if (@currentVersion > 5 and @wantedVersion <= 5)
				execute RevertAddForeignKeyToLogs
			if (@currentVersion > 4 and @wantedVersion <= 4)
				execute RevertAddCandidateKeyToLists
			if (@currentVersion > 3 and @wantedVersion <= 3)
				execute RevertAddPrimaryKeyToUsers
			if (@currentVersion > 2 and @wantedVersion <= 2)
				execute RevertAddDefaultToUserLists
			if (@currentVersion > 1 and @wantedVersion <= 1)
				execute RevertAlterUserListsOwnerColumn
			if (@currentVersion > 0 and @wantedVersion <= 0)
				execute RevertAlterUserListsOwnerColumnType
		end
	else
		begin
			if (@currentVersion < 1 and @wantedVersion >= 1)
				execute AlterUserListsOwnerColumnType
			if (@currentVersion < 2 and @wantedVersion >= 2)
				execute AlterUserListsOwnerColumn
			if (@currentVersion < 3 and @wantedVersion >= 3)
				execute AddDefaultToUserLists
			if (@currentVersion < 4 and @wantedVersion >= 4)
				execute AddPrimaryKeyToUsers
			if (@currentVersion < 5 and @wantedVersion >= 5)
				execute AddCandidateKeyToLists
			if (@currentVersion < 6 and @wantedVersion >= 6)
				execute AddForeignKeyToLogs
			if (@currentVersion < 7 and @wantedVersion >= 7)
				execute CreateMessagesTable
		end
	update [Versions]
		set [Version] = @wantedVersion
	where [Version] = @currentVersion
go


execute UpdateDatabase 0