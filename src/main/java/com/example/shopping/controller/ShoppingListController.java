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
		/*
		 * ShoppingListエンティティにデータを保存
		 */

		ShoppingList newList = new ShoppingList();

		// 買い物リストにお店Idを登録
		newList.setShopId(shoppingListForm.getShopId());

		// 日時を登録
		LocalDate createdDate = LocalDate.now();
		newList.setCreatedDate(createdDate);

		shoppingListRepository.saveAndFlush(newList);

		/*
		 * ShoppingListIngredientエンティティにデータを保存
		 */

		// shoppingListFormに入った材料IdをListに代入
		List<Long> selectedIngredientIds = shoppingListForm.getIngredientIds();

		// ShoppingListIngredientに材料Idを保存するためのArrayListを作成
		List<ShoppingListIngredient> shoppingListIngredients = new ArrayList<>();

		// ShoppingListIngredientに材料Idをセット
		for (Long id : selectedIngredientIds) {
			ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient();
			shoppingListIngredient.setIngredientId(id);
			shoppingListIngredient.setShoppingList(newList);
			shoppingListIngredients.add(shoppingListIngredient);
		}
		// ShoppingListIngredientに保存
		shoppingListIngredientRepository.saveAllAndFlush(shoppingListIngredients);

		/*
		 * ShoppingListSeasoningエンティティにデータを保存
		 */
		// shoppingListFormに入った調味料IdをListに代入
		List<Long> selectedSeasoningIds = shoppingListForm.getSeasoningIds();

		// ShoppingListSeasoningに調味料Idを保存するためにArrayListを作成
		List<ShoppingListSeasoning> shoppingListSeasonings = new ArrayList<>();

		// ShoppingListSeasoningに材料Idをセット
		for (Long id : selectedSeasoningIds) {
			ShoppingListSeasoning shoppingListSeasoning = new ShoppingListSeasoning();
			shoppingListSeasoning.setSeasoningId(id);
			shoppingListSeasoning.setShoppingList(newList);
			shoppingListSeasonings.add(shoppingListSeasoning);
		}
		// ShoppingListSeasoningに保存
		shoppingListSeasoningRepository.saveAllAndFlush(shoppingListSeasonings);

		/*
		 * ShoppingListとShoppingListIngredient/ShoppingListSeasoningの紐付け
		 */
		newList.setShoppingListIngredients(shoppingListIngredients);
		newList.setShoppingListSeasonings(shoppingListSeasonings);
		shoppingListRepository.saveAndFlush(newList);

//		return "reditect:/shoppinglist/select3"; //redirectにするとselect3に必要な情報がうまく取得できないので一旦別ページに移動
		return "shoppinglist/list";
	}

}
