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

