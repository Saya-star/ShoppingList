package com.example.shopping.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Shop;
import com.example.shopping.entity.ShoppingList;
import com.example.shopping.entity.ShoppingListIngredient;
import com.example.shopping.entity.ShoppingListSeasoning;
import com.example.shopping.form.SelectForm;
import com.example.shopping.form.ShoppingListForm;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.SeasoningRepository;
import com.example.shopping.repository.ShopRepository;
import com.example.shopping.repository.ShoppingListIngredientRepository;
import com.example.shopping.repository.ShoppingListRepository;
import com.example.shopping.repository.ShoppingListSeasoningRepository;
import com.example.shopping.service.DishService;
import com.example.shopping.service.ShoppingListService;

@Controller
@RequestMapping(value = "/shoppinglist")
public class ShoppingListController {

	@Autowired
	DishRepository dishRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	SeasoningRepository seasoningRepository;

	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	ShoppingListRepository shoppingListRepository;

	@Autowired
	ShoppingListIngredientRepository shoppingListIngredientRepository;
	
	@Autowired
	ShoppingListSeasoningRepository shoppingListSeasoningRepository;

	@Autowired
	DishService dishService;

	@Autowired
	ShoppingListService shoppingListService;

	// 料理を選択する画面の表示
	@GetMapping(value = "/select1")
	public String getPage(Dish dish, Model model) {
		model.addAttribute("title", "買い物リストを作る①");
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "shoppinglist/select1";
	}

	// 選択された料理の材料・調味料と、いつも買うものリスト、あとで買うものリストの表示
	@PostMapping(value = "/select2")
	public String getItems(@RequestParam(value = "dishIds", required = false) Long[] dishIds, Model model) {
		shoppingListService.getItems(dishIds, model);
		return "shoppinglist/select2";
	}

	// 選択した買うものから買うものリストを作成するページを表示
	@PostMapping(value = "/select3")
	public String selectItems(@ModelAttribute SelectForm selectForm, Model model) {
		shoppingListService.selectItems(selectForm, model);
		List<Shop> shopList = shopRepository.findAll();
		model.addAttribute("shopList", shopList);
		return "shoppinglist/select3";
	}

	// 買い物リストを作成（DBに保存）
	@PostMapping(value = "/select3/create")
	public String createList(@ModelAttribute ShoppingListForm shoppingListForm, Model model) {

		//DBにデータを登録
		ShoppingList createdList = shoppingListService.createShoppingList(shoppingListForm);
		//登録内容を表示するための動き
		List<ShoppingListIngredient> ingredinetList = createdList.getShoppingListIngredients();
		
//		model.addAttribute("ingredientList",createdList.getShoppingListIngredients());
//		return "reditect:/shoppinglist/select3"; //redirectにするとselect3に必要な情報がうまく取得できないので一旦別ページに移動
		return "shoppinglist/list";
	}

}
