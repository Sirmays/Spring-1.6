BEGIN;

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (id bigserial PRIMARY KEY, name VARCHAR(255), price int);
INSERT INTO customers (name) VALUES
('Vanya'),
('Petya'),
('Masha');

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), price int);
INSERT INTO products (title, price) VALUES
('bread', 10),
('milk', 20),
('carrot', 100),
('chokolate', 50),
('water', 500);

DROP TABLE IF EXISTS customers_products CASCADE;
CREATE TABLE customers_products (product_id bigint, customer_id bigint, FOREIGN KEY (product_id) REFERENCES products (id), FOREIGN KEY (customer_id) REFERENCES customers (id));
INSERT INTO customers_products (product_id, customer_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 2),
(1, 2),
(3, 2),
(4, 3),
(1, 3),
(2, 3);

COMMIT;