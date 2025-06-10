CREATE TABLE user_cart.carts
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL
);

CREATE TABLE user_cart.items
(
    id            BIGSERIAL PRIMARY KEY,
    cart_id       BIGINT         NOT NULL,
    product_id    BIGINT         NOT NULL,
    quantity      SMALLINT       NOT NULL,
    priceSnapshot NUMERIC(10, 2) NOT NULL,
    foreign key (cart_id) references carts (id)
);
CREATE SEQUENCE item_seq START WITH 1;

ALTER TABLE user_cart.items
    ALTER COLUMN id SET DEFAULT nextval('item_seq');


CREATE SEQUENCE cart_seq START WITH 1;

ALTER TABLE user_cart.carts
    ALTER COLUMN id SET DEFAULT nextval('cart_seq');


CREATE INDEX carts_user_id_idx ON user_cart.carts (user_id);
CREATE INDEX items_cart_id_idx ON user_cart.items (cart_id);

