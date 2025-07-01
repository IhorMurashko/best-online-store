INSERT INTO producer (id, producer_name) VALUES (1, 'Nestle');
INSERT INTO producer (id, producer_name) VALUES (2, 'Mars');
INSERT INTO brand (id, brand_name) VALUES (1, 'Biona');


INSERT INTO product (id, product_name, price, description, kcal, producer_id, brand_id) VALUES (1, 'Шоколадка', 3.5, 'Вкусная шоколадка', 500, 2, 1);

INSERT INTO image (id, url, product_id) VALUES (1, 'https://example.com/photo1.jpg', 1);
INSERT INTO image (id, url, product_id) VALUES (2, 'https://example.com/photo2.jpg', 1);