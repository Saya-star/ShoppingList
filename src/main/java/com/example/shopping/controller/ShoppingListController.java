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
import com.example.shopping.entity.Seasoning;
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

	// 選択した買うものを買い物リストを作成するページに表示
	@PostMapping(value = "/select3")
	public String selectItems(@ModelAttribute SelectForm selectForm, Model model) {
//		shoppingListService.selectItems(selectForm, model);
		List<Ingredient> selectedIngredient = selectForm.getIngredients();
		List<Seasoning> selectedSeasoning = selectForm.getSeasonings();
		//TODO いつも買うものリスト・あとで買うものリストの表示の処理
//		List<AlwaysBuy> selectedAlwaysBuy = selectForm.getAlwaysBuys();
		List<Shop> shopList = shopRepository.findAll();
		model.addAttribute("selectedIngredient", selectedIngredient);
		model.addAttribute("selectedSeasoning",selectedSeasoning);
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
		// 保存
		shoppingListRepository.saveAndFlush(newList);
		
		model.addAttribute("createdList",newList);

		/*
		 * ShoppingListIngredientエンティティにデータを保存
		 */

		// shoppingListFormに入っている材料IdのデータををListに代入
		Iterable<Ingredient> selectedIngredientIds = shoppingListForm.getIngredientIds();
		// ShoppingListIngredientエンティティに材料Idを保存するためのArrayListを作成
		List<ShoppingListIngredient> shoppingListIngredients = new ArrayList<>();
		// 材料Idをセット
		for (Ingredient ingredient : selectedIngredientIds) {
			ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient();
			shoppingListIngredient.setIngredient(ingredient);
			shoppingListIngredient.setShoppingList(newList);
			shoppingListIngredients.add(shoppingListIngredient);
		}
		// 保存
		shoppingListIngredientRepository.saveAllAndFlush(shoppingListIngredients);

		model.addAttribute("ingredientList",shoppingListIngredients);
		
		/*
		 * ShoppingListSeasoningエンティティにデータを保存
		 */

		// shoppingListFormに入っている調味料IdのデータをListに代入
		Iterable<Seasoning> selectedSeasoningIds = shoppingListForm.getSeasoningIds();
		// ShoppingListSeasoningエンティティに調味料Idを保存するためのArrayListを作成
		List<ShoppingListSeasoning> shoppingListSeasonings = new ArrayList<>();
		// 調味料Idをセット
		for (Seasoning seasoning : selectedSeasoningIds) {
			ShoppingListSeasoning shoppingListSeasoning = new ShoppingListSeasoning();
			shoppingListSeasoning.setSeasoning(seasoning);
			shoppingListSeasoning.setShoppingList(newList);
			shoppingListSeasonings.add(shoppingListSeasoning);
		}
		// 保存
		shoppingListSeasoningRepository.saveAllAndFlush(shoppingListSeasonings);

		model.addAttribute("seasoningList",shoppingListSeasonings);
		/*
		 * 以下、サービスクラスにメソッドを全て移動した場合
		 */
		
		// DBにデータを登録
//		ShoppingList createdList = shoppingListService.createShoppingList(shoppingListForm);
//		model.addAttribute("createdList",createdList);
		
//		// 登録内容を表示する
//		// 登録したリストからShoppingListIngredientを呼び出す
//		// shoppingListIngredientに登録されている材料Idから該当するIngredientを呼び出す
//		shoppingListIngredientRepository.findById(createdList.getShoppingListId());
//		model.addAttribute("ingredientList",shoppingListIngredientList.getClass());
//		List<ShoppingListIngredient> shoppingListIngredientList = createdList.getShoppingListIngredients();
//		List<Ingredient> registeredIngredients = new ArrayList<>();
//		for (ShoppingListIngredient shoppingListIngredient : shoppingListIngredientList) {
//			long id = shoppingListIngredient.getIngredientId();
//			Optional<Ingredient> ingredient = ingredientRepository.findById(id);
//			registeredIngredients.add(ingredient.get());
//		}
//		model.addAttribute("ingredientList",registeredIngredients);
//		
//		//登録した材料の呼び出し
//		List<ShoppingListSeasoning> shoppingListSeasoningList = createdList.getShoppingListSeasonings();
//		List<Seasoning> registeredSeasonings = new ArrayList<>();
//		for(ShoppingListSeasoning shoppingListSeasoning : shoppingListSeasoningList) {
//			long id = shoppingListSeasoning.getSeasoningId();
//			Optional<Seasoning> seasoning = seasoningRepository.findById(id);
//			registeredSeasonings.add(seasoning.get());
//		}
//		model.addAttribute("seasoningList",registeredSeasonings);
//		model.addAttribute("ingredientList",createdList.getShoppingListIngredients());
//		return "reditect:/shoppinglist/select3"; //redirectにするとselect3に必要な情報がうまく取得できないので一旦別ページに移動
		return "shoppinglist/list";
	}

}
