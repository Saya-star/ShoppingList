INSERT INTO dish (dish_id, app_user_id, dish_name, created_date, updated_date) values (1, 1, 'カレー', LOCALTIME(), LOCALTIME());
INSERT INTO dish (dish_id, app_user_id, dish_name, created_date, updated_date) values (2, 1, 'オムライス', LOCALTIME(), LOCALTIME());
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (1, '玉ねぎ', 1, '1個', LOCALTIME(), LOCALTIME(), 1);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (2, '人参', 1, '1本', LOCALTIME(), LOCALTIME(), 1);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (3, 'ジャガイモ', 1, '3個', LOCALTIME(), LOCALTIME(), 1);
INSERT INTO seasoning (seasoning_id, seasoning_name, created_date, updated_date, dish_id) values (1, 'カレールー', LOCALTIME(), LOCALTIME(), 1);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (4, '卵', 7, '2個', LOCALTIME(), LOCALTIME(), 2);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (5, '鶏もも肉', 6, '100g', LOCALTIME(), LOCALTIME(), 2);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (6, '玉ねぎ', 1, '1/2個', LOCALTIME(), LOCALTIME(), 2);
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_type, quantity, created_date, updated_date, dish_id) values (7, '米', 7, '2合', LOCALTIME(), LOCALTIME(), 2);
INSERT INTO seasoning (seasoning_id, seasoning_name, created_date, updated_date, dish_id) values (2, 'ケチャップ', LOCALTIME(), LOCALTIME(), 2);
INSERT INTO seasoning (seasoning_id, seasoning_name, created_date, updated_date, dish_id) values (3, 'しょうゆ', LOCALTIME(), LOCALTIME(), 2);

