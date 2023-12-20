INSERT INTO dish (dish_id, app_user_id, dish_name, created_date, updated_date) values (10001, 1, 'カレー', LOCALTIME(), LOCALTIME());
INSERT INTO dish (dish_id, app_user_id, dish_name, created_date, updated_date) values (10002, 1, 'オムライス', LOCALTIME(), LOCALTIME());
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10001, '玉ねぎ', 1, '1個', LOCALTIME(), LOCALTIME(), 10001);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10002, '人参', 1, '1本', LOCALTIME(), LOCALTIME(), 10001);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10003, 'ジャガイモ', 1, '3個', LOCALTIME(), LOCALTIME(), 10001);
INSERT INTO seasoning (seasoning_id, seasoning_name, created_date, updated_date, dish_id) values (10001, 'カレールー', LOCALTIME(), LOCALTIME(), 1);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10004, '卵', 7, '2個', LOCALTIME(), LOCALTIME(), 10002);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10005, '鶏もも肉', 6, '100g', LOCALTIME(), LOCALTIME(), 10002);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10006, '玉ねぎ', 1, '1/2個', LOCALTIME(), LOCALTIME(), 10002);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (10007, '米', 7, '2合', LOCALTIME(), LOCALTIME(), 10002);
INSERT INTO seasoning (seasoning_id, seasoning_name, created_date, updated_date, dish_id) values (10002, 'ケチャップ', LOCALTIME(), LOCALTIME(), 10002);
INSERT INTO seasoning (seasoning_id, seasoning_name, created_date, updated_date, dish_id) values (10003, 'しょうゆ', LOCALTIME(), LOCALTIME(), 10002);

