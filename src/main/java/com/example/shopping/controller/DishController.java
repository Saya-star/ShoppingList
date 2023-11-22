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

import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.service.DishService;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/dish")
public class DishController {

	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	IngredientRepository ingredientRepository; //1115

	@Autowired
	DishService dishService;
	
	// 料理一覧画面の表示
	@GetMapping(value = "/list")
	public String getList(Dish dish,Model model) {
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "dish/list";
	}

	// 料理登録画面の表示
	@GetMapping(value = "/register")
	public String register(@ModelAttribute Dish dish, @ModelAttribute("ingredient")Ingredient ingredient,Model model) {
		model.addAttribute("title", "料理の登録");
		model.addAttribute("dish", new Dish());
		return "dish/register";
	}

	// 料理の登録
	//1115fix
	@PostMapping(value = "/list")
	public String add(@ModelAttribute Dish dish, @ModelAttribute("ingredient")Ingredient ingredient,Model model) {
		//System.out.println("*****" + dish.getIngredient().size());
		System.out.println(ingredient.getIngredientName());
		dishRepository.saveAndFlush(dish);
		String[] ingredientNames = ingredient.getIngredientName().split(",");
		for (int i = 0; i < ingredientNames.length; i++) {
		    Ingredient saveName = new Ingredient();
		    saveName.setIngredientName(ingredientNames[i]);
		    ingredientRepository.saveAndFlush(saveName);
		}

		//ingredientRepository.saveAndFlush(ingredient);//1115 材料データの保存
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "dish/list";
	}

	// 登録した料理の詳細表示
	@GetMapping(value = "detail/{id}")
	public String getDetail(@PathVariable("id") long dishId, Dish dish,Model model) {
		model.addAttribute("title", "料理の詳細");
		Optional<Dish> data = dishService.findDish(dishId);
		//Optional<Ingredient> ingredientData = dishService.findIngredient(ingredientId);//1116追加 //*
		model.addAttribute("detail", data.get());
		//model.addAttribute("ingredient-detail",ingredientData.get());//1116追加 //*
		return "dish/detail";
	}

	// 料理の内容の編集画面
	@GetMapping(value = "edit/{id}")
	public String edit(@PathVariable("id") long id, @ModelAttribute("Form") Dish dish, Model model) {
		model.addAttribute("title", "料理の編集");
		Optional<Dish> data = dishService.findDish(id);
		model.addAttribute("Form", data.get());
		return "dish/edit";
	}

	// 編集内容を登録
	@PostMapping(value = "edit")
	public String update(@ModelAttribute Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		return "redirect:/dish/list";
	}

	// 削除メソッド
	@PostMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		dishRepository.deleteById(id);
		return "redirect:/dish/list";
	}
}