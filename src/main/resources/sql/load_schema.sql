
drop database if exists maori;
create database maori;

drop user maori;
create user maori identified by 'maori';
grant all privileges on maori.* to 'maori'@'localhost';

use maori;

create table model (
    id int,
    name text,
    model longblob
);

create table model_associations (
    id int,
    model_id int,
    client_id char(30)
);
