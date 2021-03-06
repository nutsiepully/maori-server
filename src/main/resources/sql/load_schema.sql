
drop database if exists maori;
create database maori;

drop user maori;
create user maori identified by 'maori';
grant all privileges on maori.* to 'maori'@'%' identified by 'maori' with grant option;

use maori;

create table models (
    id int not null auto_increment primary key,
    name varchar(255) not null,
    version varchar(255) not null,
    active bit,
    archive bit,
    payload longblob not null
);

-- TODO: Constraint name+version unique

create table model_devices (
    id int not null auto_increment primary key,
    model_id int not null,
    device_id varchar(255) not null
);
