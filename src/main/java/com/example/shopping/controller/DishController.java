package com.example.shopping.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.enums.IngredientType;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.SeasoningRepository;
import com.example.shopping.service.DishService;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/dish")
public class DishController {

	@Autowired
	DishRepository dishRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	SeasoningRepository seasoningRepository;

	@Autowired
	DishService dishService;

	// 料理一覧画面の表示
	@GetMapping(value = "/list")
	public String getList(Dish dish, Model model) {
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "dish/list";
	}

	// 料理登録画面の表示
	@GetMapping(value = "/register")
	public String register(@ModelAttribute Dish dish, @ModelAttribute Ingredient ingredient, Model model) {
		model.addAttribute("title", "料理の登録");
		model.addAttribute("dish", new Dish());
		model.addAttribute("ingredientType", IngredientType.SELECT_OPTION);// 材料種類のプルダウンの初期表示
		return "dish/register";
	}

	// 料理の登録
	@PostMapping(value = "/list")
	public String add(@ModelAttribute Dish dish, Model model) {
		System.out.println("add");//確認用
		List<Dish> result = dishService.add(dish);
		model.addAttribute("data", result);
		return "dish/list";
	}

	// 登録した料理の詳細表示
	@GetMapping(value = "/detail/{id}")
	public String getDetail(@PathVariable("id") long dishId, Model model) {
		model.addAttribute("title", "料理の詳細");
		// idを元に登録された料理を検索
		Optional<Dish> data = dishService.findDish(dishId);
		model.addAttribute("detail", data.get());

		// 材料と調味料の検索
		if (data.isPresent()) {
			Dish savedDish = data.get();
			List<Ingredient> ingredients = savedDish.getIngredient();
			List<Seasoning> seasonings = savedDish.getSeasoning();
			model.addAttribute("ingredientDetail", ingredients);
			model.addAttribute("seasoningDetail", seasonings);
		} // else {
			// Dishが見つからなかった場合の処理

		// }
		return "dish/detail";
	}

	// 料理の内容の編集画面の表示
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") long dishId, @ModelAttribute Dish dish, Model model) {
		model.addAttribute("title", "料理の編集");
		Optional<Dish> data = dishService.findDish(dishId);
		if (data.isPresent()) {
			model.addAttribute("dish", data.get());
			Dish savedDish = data.get();
			List<Ingredient> ingredients = savedDish.getIngredient();
			List<Seasoning> seasonings = savedDish.getSeasoning();
			model.addAttribute("ingredients", ingredients);
			model.addAttribute("seasonings", seasonings);
		}
		
		return "dish/edit";
	}

	// 編集内容を登録
	@PostMapping(value = "/edit")
	public String update(@ModelAttribute Dish form, Model model) {
		//dishRepository.saveAndFlush(dish);
		System.out.println("update");//確認用
		Optional<Dish> data = dishService.findDish(form.getDishId());
		List<Dish> result = dishService.update(form,data.get());
		//dishRepository.saveAndFlush(DishService.update(form,data.get()));
		//model.addAttribute("data", dishService.getList(form));
		model.addAttribute("data", result);
		return "redirect:/dish/list";
	}

	// 削除メソッド
	@PostMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		dishRepository.deleteById(id);
		return "redirect:/dish/list";
	}
}