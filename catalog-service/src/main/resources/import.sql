INSERT INTO producer (id, producer_name) VALUES (1, 'Nestle');
INSERT INTO producer (id, producer_name) VALUES (2, 'Mars');
INSERT INTO brand (id, brand_name) VALUES (1, 'Biona');


INSERT INTO product (id, product_name, price, description, quantity_in_stock, kcal, rating, rating_count, producer_id, brand_id) VALUES (1, 'Молоко', 23.3, 'Описание1', 100, 32, 5.6, 32, 1, 1);
INSERT INTO product (id, product_name, price, description, quantity_in_stock, kcal, rating, rating_count, producer_id, brand_id) VALUES (2, 'Хлеб', 10.3, 'Описание2', 100, 22, 7.6, 32, 2, 1);
INSERT INTO product (id, product_name, price, description, quantity_in_stock, kcal, rating, rating_count, producer_id, brand_id) VALUES (3, 'Чай', 22.6, 'Описание3', 100, 532, 2.6, 32, 1, 1);

INSERT INTO category (id, category_name) VALUES (1, 'Вкусное');
INSERT INTO category (id, category_name) VALUES (2, 'Свежее');

INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (1, 2);



INSERT INTO product_sales (id, product_id, quantity, sale_date) VALUES (1, 1,23, '2024-07-22 12:32:12');
INSERT INTO product_sales (id, product_id, quantity, sale_date) VALUES (2, 1,23, '2024-07-22 12:32:12');
INSERT INTO product_sales (id, product_id, quantity, sale_date) VALUES (3, 1,23, '2024-07-22 12:32:12');




INSERT INTO image (id, url, product_id) VALUES (1, 'https://example.com/photo1.jpg', 1);
INSERT INTO image (id, url, product_id) VALUES (2, 'https://example.com/photo2.jpg', 1);




