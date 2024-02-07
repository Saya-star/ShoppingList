package com.example.shopping.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Shop;
import com.example.shopping.entity.ShoppingList;
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

	// 作成したタイミングで買い物リストを表示する用
	private List<ShoppingList> shoppingLists;

	// セッションに格納するフォームオブジェクトの生成
	@ModelAttribute(value = "selectForm")
	public SelectForm setUpSelectForm() {
		return new SelectForm();
	}

	// セッションの初期化（操作途中のオブジェクトがセッションに格納されていたときのため）
	@GetMapping(value = "/initialize")
	public String initializeSelectForm(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/shoppinglist/select1";
	}

	// 【画面１】料理を選択する画面の表示
	@GetMapping(value = "/select1")
	public String getPage(Dish dish, Model model, Principal principal) {
		model.addAttribute("title", "買い物リストを作る①");
		List<Dish> list = dishService.getDishes(dish, principal);
		model.addAttribute("data", list);
		return "shoppinglist/select1";
	}

	// 【画面２】選択された料理の材料・調味料と、いつも買うものリスト、あとで買うものリストの表示
	@PostMapping(value = "/select2")
	public String getItems(@RequestParam(value = "dishIds", required = false) Long[] dishIds, Model model) {
		model.addAttribute("title", "買い物リストを作る②");
		model.addAttribute("ingredientList", shoppingListService.findIngredient(dishIds)); //材料の検索
		model.addAttribute("seasoningList", shoppingListService.findSeasoning(dishIds)); //調味料の検索
		model.addAttribute("alwaysBuy", alwaysBuyRepository.findAll()); // いつも買うものの検索
		return "shoppinglist/select2";
	}

	// 【画面３】選択した買うものを買い物リストを作成するページに表示
	@PostMapping(value = "/select3")
	public String selectItems(@ModelAttribute SelectForm selectForm, Model model) {
		System.out.println("create");
		shoppingLists = new ArrayList<>();//同じタイミングで作成した買い物リストだけを画面に表示するため、ここで初期化
		model.addAttribute("title", "買い物リストを作る③");
		model.addAttribute("selectedIngredient", selectForm.getIngredients());
		model.addAttribute("selectedSeasoning", selectForm.getSeasonings());
		List<Shop> shopList = shopRepository.findAll();
		model.addAttribute("shopList", shopList);
		// TODO いつも買うものリスト・あとで買うものリストの表示の処理
		model.addAttribute("selectedAlwaysBuy", selectForm.getAlwaysBuys());
		return "shoppinglist/select3";
	}

	// 【画面３・再】買い物リスト再作成時・選択した買うものを買い物リストを作成するページに表示
	@GetMapping(value = "/select3")
	public String selectItemsEdit(SelectForm selectForm, Model model) {
		System.out.println("recreate");
		model.addAttribute("title", "買い物リストを作る③");
		model.addAttribute("selectedIngredient", selectForm.getIngredients());
		model.addAttribute("selectedSeasoning", selectForm.getSeasonings());
		List<Shop> shopList = shopRepository.findAll();
		model.addAttribute("shopList", shopList);
		model.addAttribute("selectedAlwaysBuy", selectForm.getAlwaysBuys());
		// TODO あとで買うものリストの表示の処理
		
		// 登録した買い物リストを表示する
		model.addAttribute("shoppingLists", shoppingLists);
		return "shoppinglist/select3";
	}

	// 買い物リストを作成（DBに保存）
	@PostMapping(value = "/select3/create")
	public String createList(@ModelAttribute ShoppingListForm shoppingListForm, Model model, Principal principal) {
		ShoppingList newList = shoppingListService.createShoppingList(shoppingListForm, principal);
		shoppingLists.add(newList);
		return "redirect:/shoppinglist/select3";
	}

	// セッションの破棄（完了ボタンを押下したとき）
	@GetMapping(value = "/complete")
	public String complete(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/shoppinglist/list";
	}

	// 買い物リストの一覧表示
	@GetMapping(value = "/list")
	public String getShoppingLists(Model model, Principal principal) {
//		List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
		model.addAttribute("shoppingLists", shoppingListService.getShoppingLists(principal));
		return "shoppinglist/list";
	}

	// 買い物リストの削除（論理削除）
	@PostMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") long shoppingListId, Model model) {
		shoppingListService.deleteShoppingList(shoppingListId);
		return "redirect:/shoppinglist/list";
	}
}
