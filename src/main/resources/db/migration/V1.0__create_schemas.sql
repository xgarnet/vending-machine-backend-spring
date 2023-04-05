create table if not exists product (
    id int primary key,
    amount_available int,
    cost int,
    product_name varchar(100),
    seller_id int
);
create sequence if not exists product_sequence start with  1 increment by 1;

create table if not exists login (
   id int primary key,
   username varchar(100),
   password varchar(100),
   deposit int,
   role varchar(100)
);

create sequence if not exists user_sequence start with  1 increment by 1;

create table if not exists loginuser (
     id int primary key,
     firstname varchar(100),
     lastname varchar(100),
     email varchar(100),
     password varchar(100),
     role varchar(100)
);

create sequence if not exists login_sequence start with  1 increment by 1;

create table if not exists token (
     id int primary key,
     token varchar(1024),
     token_type varchar(100),
     revoked boolean,
     expired boolean,
     user_id int
);

create sequence if not exists token_sequence start with  1 increment by 1;
