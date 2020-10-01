/*CREATE TABLE Currencies
	(Id INT PRIMARY KEY,
	Name VARCHAR(3),
	Value SMALLMONEY)

CREATE TABLE Users
	(Id INT PRIMARY KEY,
	Name VARCHAR(100),
	Email VARCHAR(100),
	Mode TINYINT, /* 0 - adaptive, 1 - light, 2 - dark */
	LightStart INT,
	DarkStart INT,
	Recommendations BIT,
	Fill BIT,
	PreferredCurrencyId INT REFERENCES Currencies(Id),
	GraphColumns INT)

CREATE TABLE Lists
	(Id INT PRIMARY KEY,
	Name VARCHAR(100),
	Timestamp DATETIME)

CREATE TABLE UserLists
	(UserId INT REFERENCES Users(Id),
	ListId INT REFERENCES Lists(Id),
	PRIMARY KEY(UserId, ListId),
	Owner BIT,
	Write BIT)

CREATE TABLE Connections
	(FromListId INT REFERENCES Lists(Id),
	ToListId INT REFERENCES Lists(Id),
	PRIMARY KEY(FromListId, ToListId))

CREATE TABLE Categories
	(Id INT PRIMARY KEY,
	UserId INT REFERENCES Users(Id),
	Name VARCHAR(100))

CREATE TABLE ListItems
	(Id INT PRIMARY KEY,
	Name VARCHAR(100),
	Price SMALLMONEY,
	CurrencyId INT REFERENCES Currencies(Id),
	CategoryId INT REFERENCES Categories(Id),
	Timestamp DATETIME)

CREATE TABLE Products
	(Id INT PRIMARY KEY,
	UserId INT REFERENCES Users(Id),
	CategoryId INT REFERENCES Categories(Id),
	Name VARCHAR(100))

CREATE TABLE Prices
	(Id INT PRIMARY KEY,
	CurrencyId INT REFERENCES Currencies(Id),
	ProductId INT REFERENCES Products(Id),
	Price SMALLMONEY,
	Appearances INT)

CREATE TABLE ProductPrices
	(ProductId INT REFERENCES Products(Id),
	PriceId INT REFERENCES Prices(Id),
	PRIMARY KEY(ProductId, PriceId),
	Timestamp DATETIME,
	Enforced BIT)

CREATE TABLE Excels
	(Id INT PRIMARY KEY,
	ListId INT REFERENCES Lists(Id),
	Timestamp DATETIME,
	Products INT)

CREATE TABLE UserExcels
	(UserId INT REFERENCES Users(Id),
	ExcelId INT REFERENCES Excels(Id),
	PRIMARY KEY(UserId, ExcelId),
	Owner BIT)
*/