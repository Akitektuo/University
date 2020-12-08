create table [Ta] (
	[aid] int primary key identity(1, 1),
	[a2] int unique,
	[a3] int
)

create table [Tb] (
	[bid] int primary key identity(1, 1),
	[b2] int,
	[b3] int
)

create table [Tc] (
	[cid] int primary key identity(1, 1),
	[aid] int,
	[bid] int
)

--a)
--clustered index scan
select [aid] from [Ta]

--clustered index seek
select [aid] from [Ta] where [aid] > 5

--nonclustered index scan
select [a2] from [Ta]

--nonclustered index seek
select [a2] from [Ta] where [a2] > 1

--key lookup
select * from [Ta]

--b)
select [b2] from [Tb] where [b2] = 5

if exists(select * from sys.indexes where [name] = 'index_b2')
	drop index [index_b2] on [Tb]
create nonclustered index [index_b2] on [Tb]([b2])

select [b2] from [Tb] where [b2] = 5

--c)
drop view [View_TaTC]
create view [View_TaTc] as
    select [Tc].[cid], [Tc].[aid]
    from [Tc] inner join [Ta] on [Ta].[aid] = [Tc].[aid]
    where [Tc].[aid] between 1 and 100
go

select * from [View_TaTc]

if exists(select * from sys.indexes where [name] = 'index_aid')
	drop index [index_aid] on [Tc]
create nonclustered index [index_aid] on [Tc]([aid])

select * from [View_TaTc]