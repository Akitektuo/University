CREATE TABLE Users
	(Id INT PRIMARY KEY,
	Name VARCHAR(100),
	Email VARCHAR(100))

CREATE TABLE Currencies
	(Id INT PRIMARY KEY,
	Name VARCHAR(3))

CREATE TABLE CurrencyHistory
	(CurrencyId INT REFERENCES Currencies(Id),
	Date DATETIME,
	primary key(CurrencyId, Date),
	Value SMALLMONEY)

CREATE TABLE Categories
	(Id INT PRIMARY KEY,
	Name VARCHAR(100))

CREATE TABLE Lists
	(Id INT PRIMARY KEY,
	Name VARCHAR(100))

create table Products
	(Id INT PRIMARY KEY,
	CategoryId INT REFERENCES Categories(Id),
	CurrencyId INT REFERENCES Currencies(Id),
	Name VARCHAR(100),
	Price SMALLMONEY)

CREATE TABLE ListProducts
	(ListId INT references Lists(Id),
	ProductId INT references Products(Id),
	PRIMARY KEY (ListId, ProductId),
	Quantity int,
	Timestamp DATETIME)

CREATE TABLE UserLists
	(UserId INT REFERENCES Users(Id),
	ListId INT REFERENCES Lists(Id),
	PRIMARY KEY(UserId, ListId),
	Owner BIT,
	Write BIT)

create table UserConnections
	(UserAId INT REFERENCES Users(Id),
	UserBId INT REFERENCES Users(Id),
	PRIMARY KEY(UserAId, UserBId),
	check (UserAId < UserBId))

Create Table Logs
	(Id int primary key,
	UserId int references Users(Id),
	Message varchar(256),
	Timestamp DATETIME)