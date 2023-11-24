DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS ingredient;

CREATE TABLE IF NOT EXISTS dish (
 ID SERIAL NOT NULL,
 APP_USER_ID BIGINT,
 DISH_NAME VARCHAR (100),
 created_date timestamp,
 updated_date timestamp,
 PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS ingredient (
 ID SERIAL NOT NULL,
 APP_USER_ID BIGINT,
 INGREDIENT_NAME VARCHAR(100),
 created_date timestamp,
 updated_date timestamp,
 PRIMARY KEY(ID)
);