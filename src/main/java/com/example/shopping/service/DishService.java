package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.shopping.entity.Dish;
import com.example.shopping.entity.UserInf;
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
	public List<Dish> getDishes(Principal principal) {
		// ログイン中のユーザーが登録した料理のみ一覧表示
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return dishRepository.findAllByUserId(user.getUserId());
	}

	// 料理の登録
	public List<Dish> add(Dish dish, Principal principal) {
		// ログイン情報を取得し、ユーザーの紐付け
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		dish.setUserId(user.getUserId());

		// 材料と調味料のテーブルにDishIdを保存
		dish.getIngredient().stream().forEach(ingredient -> ingredient.setDish(dish));
		dish.getSeasoning().stream().forEach(seasoning -> seasoning.setDish(dish));

		// 各テーブルに登録日を保存
		LocalDate createdDate = LocalDate.now();
		dish.setCreatedDate(createdDate);
		dish.getIngredient().stream().forEach(ingredient -> ingredient.setCreatedDate(createdDate));
		dish.getSeasoning().stream().forEach(seasoning -> seasoning.setCreatedDate(createdDate));

		// 料理の保存
		dishRepository.saveAndFlush(dish);

		return dishRepository.findAllByUserId(user.getUserId());
	}

	// idをキーに料理を探す
	public Optional<Dish> findDish(long id) {
		return dishRepository.findById(id);
	}

	// 編集
	public List<Dish> edit(Dish form, Principal principal) {
		// ログイン情報を取得
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();

		// 更新前のデータを取得
		Optional<Dish> currentDish = dishRepository.findById(form.getDishId());
		// 編集内容を登録
		currentDish.get().setDishId(form.getDishId());
		currentDish.get().setDishName(form.getDishName());

		// 登録されていた材料と調味料を論理削除
		currentDish.get().getIngredient().stream().forEach(ingredient -> ingredient.setIngredientDeleted(true));
		currentDish.get().getSeasoning().stream().forEach(ingredient -> ingredient.setSeasoningDeleted(true));

		// 送信された材料・調味料を元のデータに登録
		currentDish.get().setIngredient(form.getIngredient());
		currentDish.get().setSeasoning(form.getSeasoning());

		// 材料と調味料のテーブルにDishIdを保存
		currentDish.get().getIngredient().stream().forEach(ingredient -> ingredient.setDish(form));
		currentDish.get().getSeasoning().stream().forEach(seasoning -> seasoning.setDish(form));

		// 各テーブルに更新日を保存
		LocalDate updatedDate = LocalDate.now();
		currentDish.get().setUpdatedDate(updatedDate);
		currentDish.get().getIngredient().stream().forEach(ingredient -> ingredient.setUpdatedDate(updatedDate));
		currentDish.get().getSeasoning().stream().forEach(seasoning -> seasoning.setUpdatedDate(updatedDate));

		// 編集内容の保存
		dishRepository.saveAndFlush(currentDish.get());

		return dishRepository.findAllByUserId(user.getUserId());
	}
	
	//料理の削除(論理削除)
	public void delete (long dishId) {
		Optional<Dish> deleteDish = dishRepository.findById(dishId);
		if(deleteDish.isPresent()) {
			deleteDish.get().setDishDeleted(true);
			deleteDish.get().getIngredient().forEach(ingredient -> ingredient.setIngredientDeleted(true));
			deleteDish.get().getSeasoning().forEach(seasoning -> seasoning.setSeasoningDeleted(true));
		}
		dishRepository.saveAndFlush(deleteDish.get());
	}
}
