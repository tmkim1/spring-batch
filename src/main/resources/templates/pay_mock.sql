create table pay (
                     id         bigint not null auto_increment,
                     amount     bigint,
                     tx_name     varchar(255),
                     tx_date_time datetime,
                     primary key (id)
) engine = InnoDB;

insert into pay (amount, tx_name, tx_date_time) VALUES (1000, 'trade1', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (2000, 'trade2', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (3000, 'trade3', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (4000, 'trade4', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (5000, 'trade5', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (6000, 'trade6', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (7000, 'trade7', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (8000, 'trade8', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (9000, 'trade9', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (10000, 'trade10', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (11000, 'trade11', '2018-09-10 00:00:00');
insert into pay (amount, tx_name, tx_date_time) VALUES (12000, 'trade12', '2018-09-10 00:00:00');