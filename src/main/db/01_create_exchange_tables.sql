use crypto;

drop table if exists `ExchangeMarket`;
drop table if exists `Exchange`;

create table if not exists `Exchange` (
	Id int auto_increment not null,
    Name varchar(100) not null,
    Url varchar(100),
    Created datetime,
    primary key (Id)
);

create table if not exists ExchangeMarket (
	Id int auto_increment not null,
    ExchangeId int not null,
    Name varchar(100) not null,
    Pair varchar (50) not null,
    Created datetime,
    primary key (Id),
    constraint fk_ExchangeId foreign key (ExchangeId) references `Exchange`(Id)
);

create index ix_ExchangeMarket_Exchange on `ExchangeMarket` (ExchangeId);