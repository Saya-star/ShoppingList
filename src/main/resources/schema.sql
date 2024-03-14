DROP TABLE IF EXISTS shopping_list_always_buy CASCADE;
DROP TABLE IF EXISTS shopping_list_seasoning CASCADE;
DROP TABLE IF EXISTS shopping_list_ingredient CASCADE;
DROP TABLE IF EXISTS shopping_list CASCADE;
DROP TABLE IF EXISTS later_buy CASCADE;
DROP TABLE IF EXISTS always_buy CASCADE;
DROP TABLE IF EXISTS shop CASCADE;
DROP TABLE IF EXISTS seasoning CASCADE;
DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS dish CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
 user_id SERIAL NOT NULL,
 username VARCHAR(255) NOT NULL,
 name VARCHAR(255) NOT NULL,
 password VARCHAR(255) NOT NULL,
 authority VARCHAR(255) NOT NULL,
 created_date TIMESTAMP,
 PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS dish (
 dish_id SERIAL NOT NULL,
 user_id BIGINT,
 dish_name VARCHAR (100),
 created_date TIMESTAMP,
 updated_date TIMESTAMP,
 dish_deleted BOOLEAN,
 PRIMARY KEY(dish_id)
);

ALTER TABLE dish ADD CONSTRAINT FK_users_dish FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE IF NOT EXISTS ingredient (
 ingredient_id SERIAL NOT NULL,
 ingredient_name VARCHAR(100),
 ingredient_type INT,
 quantity VARCHAR(100),
 created_date TIMESTAMP,
 updated_date TIMESTAMP,
 dish_id BIGINT,
 ingredient_deleted BOOLEAN,
 PRIMARY KEY(ingredient_id)
); 

ALTER TABLE ingredient ADD CONSTRAINT FK_dish_ingredient FOREIGN KEY (dish_id) REFERENCES dish;

CREATE TABLE IF NOT EXISTS seasoning (
 seasoning_id SERIAL NOT NULL,
 seasoning_name VARCHAR(100),
 created_date TIMESTAMP,
 updated_date TIMESTAMP,
 dish_id BIGINT,
 seasoning_deleted BOOLEAN,
 PRIMARY KEY(seasoning_id)
); 

ALTER TABLE seasoning ADD CONSTRAINT FK_dish_seasoning FOREIGN KEY (dish_id) REFERENCES dish;

CREATE TABLE IF NOT EXISTS shop (
 shop_id SERIAL NOT NULL,
 user_id BIGINT,
 shop_name VARCHAR(20),
 created_date TIMESTAMP,
 deleted BOOLEAN,
 PRIMARY KEY(shop_id)
);

ALTER TABLE shop ADD CONSTRAINT FK_users_shop FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE IF NOT EXISTS always_buy (
 always_buy_id SERIAL NOT NULL,
 user_id BIGINT,
 always_buy_name VARCHAR(20),
 created_date TIMESTAMP,
 deleted BOOLEAN,
 PRIMARY KEY(always_buy_id)
);

ALTER TABLE always_buy ADD CONSTRAINT FK_users_always_buy FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE IF NOT EXISTS later_buy (
 later_buy_id SERIAL NOT NULL,
 user_id BIGINT,
 later_buy_name VARCHAR(20),
 created_date TIMESTAMP,
 deleted BOOLEAN,
 PRIMARY KEY(later_buy_id)
);

ALTER TABLE later_buy ADD CONSTRAINT FK_users_later_buy FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE IF NOT EXISTS shopping_list (
 shopping_list_id SERIAL NOT NULL,
 user_id BIGINT,
 shop_id BIGINT,
 created_date TIMESTAMP,
 deleted BOOLEAN,
 PRIMARY KEY(shopping_list_id)
);

ALTER TABLE shopping_list ADD CONSTRAINT FK_users_shopping_list FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE shopping_list ADD CONSTRAINT FK_shop_shopping_list FOREIGN KEY (shop_id) REFERENCES shop;

CREATE TABLE IF NOT EXISTS shopping_list_ingredient (
 shopping_list_ingredient_id SERIAL NOT NULL,
 ingredient_id BIGINT,
 shopping_list_id BIGINT,
 deleted BOOLEAN,
 PRIMARY KEY(shopping_list_ingredient_id)
);

ALTER TABLE shopping_list_ingredient ADD CONSTRAINT FK_shopping_list_sl_ingredient FOREIGN KEY (shopping_list_id) REFERENCES shopping_list;
ALTER TABLE shopping_list_ingredient ADD CONSTRAINT FK_ingredient_sl_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient;

CREATE TABLE IF NOT EXISTS shopping_list_seasoning (
 shopping_list_seasoning_id SERIAL NOT NULL,
 seasoning_id BIGINT,
 shopping_list_id BIGINT,
 deleted BOOLEAN,
 PRIMARY KEY(shopping_list_seasoning_id)
);

ALTER TABLE shopping_list_seasoning ADD CONSTRAINT FK_shopping_list_sl_seasoning FOREIGN KEY (shopping_list_id) REFERENCES shopping_list;
ALTER TABLE shopping_list_seasoning ADD CONSTRAINT FK_seasoning_sl_seasoning FOREIGN KEY (seasoning_id) REFERENCES seasoning;

CREATE TABLE IF NOT EXISTS shopping_list_always_buy (
 shopping_list_always_buy_id SERIAL NOT NULL,
 always_buy_id BIGINT,
 shopping_list_id BIGINT,
 deleted BOOLEAN,
 PRIMARY KEY(shopping_list_always_buy_id)
);

ALTER TABLE shopping_list_always_buy ADD CONSTRAINT FK_shopping_list_sl_always_buy FOREIGN KEY (shopping_list_id) REFERENCES shopping_list;
ALTER TABLE shopping_list_always_buy ADD CONSTRAINT FK_always_buy_sl_always_buy FOREIGN KEY (always_buy_id) REFERENCES always_buy;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO shoppinglist;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO shoppinglist;