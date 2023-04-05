create table if not exists product (
    id int primary key,
    amountAvailable int,
    cost int,
    productName varchar(100),
    sellerId int
);

INSERT INTO product(id, amountavailable, cost, productname, sellerid)
VALUES (1, 10, 25, 'Cake', 1);

INSERT INTO product(id, amountavailable, cost, productname, sellerid)
VALUES (2, 20, 50, 'Coke', 2);

create sequence if not exists product_sequence  start with  1 increment by 1;
