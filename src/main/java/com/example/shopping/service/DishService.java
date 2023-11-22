package com.example.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;

//料理の登録ページのコントローラー
@Service
public class DishService {

	@Autowired
	DishRepository dishRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	// 保存してある料理リストの取り出し
	public List<Dish> getList(Dish dish) {
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
