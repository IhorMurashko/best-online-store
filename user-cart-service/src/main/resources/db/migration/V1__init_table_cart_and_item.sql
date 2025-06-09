CREATE TABLE carts
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL
);

CREATE TABLE items
(
    id            BIGSERIAL PRIMARY KEY,
    cart_id       BIGINT         NOT NULL,
    product_id    BIGINT         NOT NULL,
    quantity      SMALLINT       NOT NULL,
    priceSnapshot NUMERIC(10, 2) NOT NULL,
    foreign key (cart_id) references carts (id)
);
CREATE SEQUENCE item_seq START WITH 1;

ALTER TABLE items
    ALTER COLUMN id SET DEFAULT nextval('item_seq');


CREATE SEQUENCE cart_seq START WITH 1;

ALTER TABLE carts
    ALTER COLUMN id SET DEFAULT nextval('cart_seq');


CREATE INDEX carts_user_id_idx ON carts (user_id);
CREATE INDEX items_cart_id_idx ON items (cart_id);

