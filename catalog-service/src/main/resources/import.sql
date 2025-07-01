INSERT INTO producer (id, producer_name) VALUES (1, 'Nestle');
INSERT INTO producer (id, producer_name) VALUES (2, 'Mars');
INSERT INTO brand (id, brand_name) VALUES (1, 'Biona');


INSERT INTO product (id, product_name, price, description, kcal, producer_id, brand_id, quantityInStock, rating, ratingCount, salesCount) VALUES (1, 'Шоколадка', 3.5, 'Вкусная шоколадка', 500, 2, 1, 40, 5.5, 10, );

INSERT INTO image (id, url, product_id) VALUES (1, 'https://example.com/photo1.jpg', 1);
INSERT INTO image (id, url, product_id) VALUES (2, 'https://example.com/photo2.jpg', 1);

/*Протестировать такой маппинг*/
INSERT INTO product (product_name, description, kcal, brand_id, quantity_in_stock, rating, rating_count, producer_id)
VALUES ('Apple', 'Fresh red apples', 52, 1, 100, 4.5, 120, 1);

INSERT INTO product (product_name, description, kcal, brand_id, quantity_in_stock, rating, rating_count, producer_id)
VALUES ('Orange Juice', 'Natural orange juice', 45, 2, 200, 4.7, 150, 2);