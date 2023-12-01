package com.example.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.enums.IngredientType;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.SeasoningRepository;

//料理の登録ページのコントローラー
@Service
public class DishService {

	@Autowired
	DishRepository dishRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	SeasoningRepository seasoningRepository;

	// 保存してある料理リストの取り出し
	public List<Dish> getList(Dish dish) {
		return dishRepository.findAll();
	}

	// 登録
	public List<Dish> add(Dish dish) {
		// 料理名の保存
		dishRepository.saveAndFlush(dish);

//		System.out.println(ingredient.getIngredientName());//材料のデータ確認用
//		System.out.println(ingredientType.getTypeId());//材料種類の確認用
//		
//		// 材料の保存
//		String[] ingredientNames = ingredient.getIngredientName().split(",");
//		String[] quantities = ingredient.getQuantity().split(",");
//		for (int i = 0; i < ingredientNames.length; i++) {
//			Ingredient save = new Ingredient();
//			save.setIngredientName(ingredientNames[i]);
//			save.setQuantity(quantities[i]);
//			save.setIngredientType(ingredientType);//追加
//			save.setDish(dish);
//			ingredientRepository.saveAndFlush(save);
//		}

		ingredientRepository.saveAllAndFlush(dish.getIngredient());

		// 調味料の保存
		seasoningRepository.saveAllAndFlush(dish.getSeasoning());

		return dishRepository.findAll();
	}

	public Optional<Dish> findDish(long id) {
		return dishRepository.findById(id);
	}

	// 1116追加
	public Optional<Ingredient> findIngredient(long id) {
		return ingredientRepository.findById(id);
	}
}
