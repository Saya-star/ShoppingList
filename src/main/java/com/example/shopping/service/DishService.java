package com.example.shopping.service;

import java.time.LocalDate;
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

	// 料理の登録
	public List<Dish> add(Dish dish) {
		// 材料と調味料のテーブルにDishIdを保存
		dish.getIngredient().stream().forEach(ingredient -> ingredient.setDish(dish));
		dish.getSeasoning().stream().forEach(seasoning -> seasoning.setDish(dish));
		
		//各テーブルに日付を保存
		LocalDate createdDate = LocalDate.now();
		dish.setCreatedDate(createdDate);
		dish.getIngredient().stream().forEach(ingredient -> ingredient.setCreatedDate(createdDate));
		dish.getSeasoning().stream().forEach(seasoning -> seasoning.setCreatedDate(createdDate));
		
		
		// 料理名の保存
		dishRepository.saveAndFlush(dish);

		//材料の保存
		//ingredientRepository.saveAllAndFlush(dish.getIngredient());//不要みたい

		// 調味料の保存
		//seasoningRepository.saveAllAndFlush(dish.getSeasoning());//不要みたい

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
