DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS seasoning;
DROP TABLE IF EXISTS shop;

CREATE TABLE IF NOT EXISTS dish (
 dish_id SERIAL NOT NULL,
 app_user_id BIGINT,
 dish_name VARCHAR (100),
 created_date timestamp,
 updated_date timestamp,
 dish_deleted BOOLEAN,
 PRIMARY KEY(dish_id)
);

CREATE TABLE IF NOT EXISTS ingredient (
 ingredient_id SERIAL NOT NULL,
 ingredient_name VARCHAR(100),
 ingredient_type INT,
 quantity VARCHAR(100),
 created_date timestamp,
 updated_date timestamp,
 dish_id BIGINT,
 ingredient_deleted BOOLEAN,
 PRIMARY KEY(ingredient_id)
); 

CREATE TABLE IF NOT EXISTS seasoning (
 seasoning_id SERIAL NOT NULL,
 seasoning_name VARCHAR(100),
 created_date timestamp,
 updated_date timestamp,
 dish_id BIGINT,
 seasoning_deleted BOOLEAN,
 PRIMARY KEY(seasoning_id)
); 

CREATE TABLE IF NOT EXISTS shop (
 shop_id SERIAL NOT NULL,
 app_user_id BIGINT,
 shop_name VARCHAR(20),
 created_date timestamp,
 deleted BOOLEAN,
 PRIMARY KEY(shop_id)
);