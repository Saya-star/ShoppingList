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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
@SessionAttributes(types = { SelectForm.class })
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

	@ModelAttribute(value = "selectForm")
	public SelectForm setUpSelectForm() {
		return new SelectForm();
	}
	
	private List<ShoppingList> shoppingLists = new ArrayList<>();

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
	public String selectItems(@ModelAttribute SelectForm selectForm, Model model,
			RedirectAttributes redirectAttributes) {
		List<Shop> shopList = shopRepository.findAll();
		model.addAttribute("selectedIngredient", selectForm.getIngredients());
		model.addAttribute("selectedSeasoning", selectForm.getSeasonings());
		model.addAttribute("shopList", shopList);
		// TODO いつも買うものリスト・あとで買うものリストの表示の処理
		return "shoppinglist/select3";
	}

	// TODO 後で修正してください
	// リダイレクト後・選択した買うものを買い物リストを作成するページに表示
	@GetMapping(value = "/select3")
	public String selectItemsEdit(SelectForm selectForm, Model model) {
		List<Shop> shopList = shopRepository.findAll();
		model.addAttribute("selectedIngredient", selectForm.getIngredients());
		model.addAttribute("selectedSeasoning", selectForm.getSeasonings());
		model.addAttribute("shopList", shopList);
		// TODO いつも買うものリスト・あとで買うものリストの表示の処理
		//先に登録された買い物リストを表示する
		model.addAttribute("shoppingLists", model.getAttribute("shoppingLists"));
		return "shoppinglist/select3";
	}

	// 買い物リストを作成（DBに保存）
	@PostMapping(value = "/select3/create")
	public String createList(@ModelAttribute ShoppingListForm shoppingListForm, Model model) {
		ShoppingList newList = shoppingListService.createShoppingList(shoppingListForm);
		shoppingLists.add(newList);
		model.addAttribute("shoppingLists", shoppingLists);
		return "forward:/shoppinglist/select3";
	}
}
