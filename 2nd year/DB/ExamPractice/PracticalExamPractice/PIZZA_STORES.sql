drop table [Deliveries]
drop table [PizzaShops]
drop table [Customers]
drop table [Drones]
drop table [Models]
drop table [Manufacturers]

create table [Manufacturers]
	([Id] int primary key,
	[Name] varchar(64))

create table [Models]
	([Id] int primary key,
	[Name] varchar(64),
	[Battery] int,
	[Speed] int,
	[ManufacturerId] int references [Manufacturers]([Id]))

create table [Drones]
	([Id] int primary key,
	[SerialNumber] varchar(64) unique,
	[ModelId] int references [Models]([Id]))

create table [Customers]
	([Id] int primary key,
	[Name] varchar(64),
	[Score] int)

create table PizzaShops
	([Id] int primary key,
	[Name] varchar(64),
	[Address] varchar(256))

create table Deliveries
	([Id] int primary key,
	[PizzaShopId] int references [PizzaShops]([Id]),
	[CustomerId] int references [Customers]([Id]),
	[DroneId] int references [Drones]([Id]),
	[DateTime] datetime)