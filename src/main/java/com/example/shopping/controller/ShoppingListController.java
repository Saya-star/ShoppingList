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
import com.example.shopping.entity.Seasoning;
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
		return "shoppinglist/select1";
	}
	
	//選択された料理の材料・調味料と、いつも買うものリスト、あとで買うものリストの表示
	@PostMapping(value="/select2")
	public String getItems(@RequestParam(value = "dishIds", required = false) Long[] dishIds, Model model) {
		// 選択されたdishIdを表示する //あとで消す
		if (dishIds != null) {
			System.out.println(Arrays.toString(dishIds));
		}
		
		List<Ingredient> ingredientList = new ArrayList<>();
		List<Seasoning> seasoningList = new ArrayList<>();
		
		for(int i = 0; i < dishIds.length; i++) {
			//dishIdから選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);
			
			//材料を検索し、ingredientListに追加
			List<Ingredient> selectedDishIngredient = selectedDish.get().getIngredient();
			ingredientList.addAll(selectedDishIngredient);
			
			//調味料を検索し、seasoningListに追加
			List<Seasoning> selectedDishSeasoning = selectedDish.get().getSeasoning();
			seasoningList.addAll(selectedDishSeasoning);
			}
		
		model.addAttribute("ingredientList",ingredientList);
		model.addAttribute("seasoningList",seasoningList);

		//いつも買うものリストをalwaysBuyListに追加
		List<AlwaysBuy> alwaysBuyList = alwaysBuyRepository.findAll();
		model.addAttribute("alwaysBuy", alwaysBuyList);
		//あとで買うものをリストに詰める　//あとで買うものリストを作ってから着手
		
		return "shoppinglist/select2";
	}
	
	@PostMapping(value="/select3")
	public String selectItems(@ModelAttribute SelectForm selectForm, Model model) {
		
		return "shoppinglist/select3";
	}
}
