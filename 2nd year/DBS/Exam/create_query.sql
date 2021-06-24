CREATE DATABASE ProjectManager

CREATE TABLE Developers(
	Id INT PRIMARY KEY,
	FirstName VARCHAR(128),
	LastName VARCHAR(128));

CREATE TABLE Projects(
	Id INT PRIMARY KEY,
	StartDate DATE,
	EndDate DATE);

CREATE TABLE ProjectDevelopers(
	ProjectId INT REFERENCES Projects(Id),
	DeveloperId INT REFERENCES Developers(Id),
	PRIMARY KEY(ProjectId, DeveloperId));

CREATE TABLE TaskTypes(
	Id INT PRIMARY KEY,
	[Name] varchar(128),
	[Description] varchar(512));

CREATE TABLE TaskPriorities(
	Id INT PRIMARY KEY,
	[Name] varchar(128),
	[Description] varchar(512));

CREATE TABLE Tasks(
	Id INT PRIMARY KEY,
	Title varchar(256),
	[Description] varchar(1024),
	[Status] varchar(16),
	ProjectId INT REFERENCES Projects(Id),
	DeveloperId INT REFERENCES Developers(Id),
	TaskTypeId INT REFERENCES TaskTypes(Id),
	TaskPriorityId INT REFERENCES TaskPriorities(Id));