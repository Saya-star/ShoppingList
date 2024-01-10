package com.example.shopping.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.form.SelectForm;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.service.DishService;

@Controller
@RequestMapping(value="/shoppinglist")
public class ShoppingListController {
	
	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	DishService dishService;
	
	@Autowired
	IngredientRepository ingredientRepository;
	
	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	//料理を選択する画面の表示
	@GetMapping(value="/select1")
	public String getPage(Dish dish, Model model) {
		model.addAttribute("title","買い物リストを作る①");
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
//		model.addAttribute("SelectForm", new SelectForm());
		return "shoppinglist/select1";
	}
	
//	//選択された料理の送信
//	@PostMapping(value="/select2")
//	public String selectDish(@ModelAttribute SelectForm selectForm, Model model) {
//		System.out.println("selected");
//		System.out.println(selectForm.getDishNames().toString());
//		return "shoppinglist/select2";
//	}
	
	@PostMapping(value="/select2")
	public String selectDish(@RequestParam(value = "dishIds", required = false) Long[] dishIds, Model model) {
		// dishNamesを利用して何かを行う
		if (dishIds != null) {
			System.out.println(Arrays.toString(dishIds));
		}
		//dishIdから材料を検索
		List<Ingredient> ingredientList = new ArrayList<>();
		
		for(int i = 0; i < dishIds.length; i++) {
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);
			List<Ingredient> selectedDishIngredient = selectedDish.get().getIngredient();
			ingredientList.addAll(selectedDishIngredient);
		}
		model.addAttribute("ingredientList",ingredientList);
		
		
		//dishIdから調味料を検索
		
		//調味料をリストに詰める
		
		//いつも買うものリストをリストに詰める
		List<AlwaysBuy> alwaysBuyList = alwaysBuyRepository.findAll();
		model.addAttribute("alwaysBuy", alwaysBuyList);
		//あとで買うものをリストに詰める
		return "shoppinglist/select2";
	}
	
	@GetMapping(value="/select2")
	public String getPage2(Model model) {
		return "shoppinglist/select2";
	}
}
