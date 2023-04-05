create table if not exists product (
    id int primary key,
    amount_available int,
    cost int,
    product_name varchar(100),
    seller_id int
);

INSERT INTO product(id, amount_available, cost, product_name, seller_id)
VALUES (1, 10, 25, 'Cake', 1);

INSERT INTO product(id, amount_available, cost, product_name, seller_id)
VALUES (2, 20, 50, 'Coke', 2);

create sequence if not exists product_sequence start with  1 increment by 1;
