package com.example.shopping.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.entity.ShoppingList;
import com.example.shopping.entity.ShoppingListIngredient;
import com.example.shopping.entity.ShoppingListSeasoning;
import com.example.shopping.form.SelectForm;
import com.example.shopping.form.ShoppingListForm;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.SeasoningRepository;
import com.example.shopping.repository.ShoppingListIngredientRepository;
import com.example.shopping.repository.ShoppingListRepository;
import com.example.shopping.repository.ShoppingListSeasoningRepository;

@Service
public class ShoppingListService {

	// TODO コンストラクタインジェクションに修正
	@Autowired
	DishRepository dishRepository;

	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	SeasoningRepository seasoningRepository;

	@Autowired
	ShoppingListRepository shoppingListRepository;

	@Autowired
	ShoppingListIngredientRepository shoppingListIngredientRepository;

	@Autowired
	ShoppingListSeasoningRepository shoppingListSeasoningRepository;

	// 選択された料理の材料・調味料と、いつも買うものリスト、あとで買うものリストの表示
	public void getItems(@RequestParam(value = "dishIds", required = false) Long[] dishIds, Model model) {

		List<Ingredient> ingredientList = new ArrayList<>();
		List<Seasoning> seasoningList = new ArrayList<>();

		// 選択された料理から材料と調味料を検索
		for (int i = 0; i < dishIds.length; i++) {
			// dishIdで選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);

			// 選択された料理に必要な材料を検索し、ingredientListに追加
			List<Ingredient> selectedDishIngredient = selectedDish.get().getIngredient();
			ingredientList.addAll(selectedDishIngredient);

			// 選択された料理に必要な調味料を検索し、seasoningListに追加
			List<Seasoning> selectedDishSeasoning = selectedDish.get().getSeasoning();
			seasoningList.addAll(selectedDishSeasoning);
		}

		// TODO 以下の処理はControllerクラスで実装する
		model.addAttribute("ingredientList", ingredientList);
		model.addAttribute("seasoningList", seasoningList);

		// いつも買うものリストをalwaysBuyListに追加
		List<AlwaysBuy> alwaysBuyList = alwaysBuyRepository.findAll();

		// TODO 以下の処理はControllerクラスで実装する
		model.addAttribute("alwaysBuy", alwaysBuyList);

		// TODO あとで買うものをリストに詰める //あとで買うものリストを作ってから着手

	}

//	public void selectItems(@ModelAttribute SelectForm selectForm, Model model) {
//		// 選択された材料Idから材料名を検索し、Listに登録 //Ingredient型のリストに変更
//		List<Ingredient> selectedIngredientList = new ArrayList<>();
//		for (Long id : selectForm.getIngredientIds()) {
//			Optional<Ingredient> selectedIngredient = ingredientRepository.findById(id);
//			selectedIngredientList.add(selectedIngredient.get());
//		}
//		// TODO 以下の処理はControllerクラスで実装する
//		model.addAttribute("selectedIngredient", selectedIngredientList);
//
//		// 選択された調味料Idから調味料名を検索し、Listに登録 //Seasoning型のリストに変更
//		List<Seasoning> selectedSeasoningList = new ArrayList<>();
//		for (Long id : selectForm.getSeasoningIds()) {
//			Optional<Seasoning> selectedSeasoning = seasoningRepository.findById(id);
//			selectedSeasoningList.add(selectedSeasoning.get());
//		}
//		// TODO 以下のリストはControllerクラスで実装する
//		model.addAttribute("selectedSeasoning", selectedSeasoningList);
//
//		// TODO いつも買うものリストから選択されたものをListに登録
//
//		// TODO あとで買うものリストから選択されたものをListに登録
//
//	}

//	public ShoppingList createShoppingList(ShoppingListForm shoppingListForm) {
//
//		/*
//		 * ShoppingListエンティティにデータを保存
//		 */
//
//		ShoppingList newList = new ShoppingList();
//		// 買い物リストにお店Idを登録
//		newList.setShopId(shoppingListForm.getShopId());
//		// 日時を登録
//		LocalDate createdDate = LocalDate.now();
//		newList.setCreatedDate(createdDate);
//		// 保存
//		shoppingListRepository.saveAndFlush(newList);
//
//		/*
//		 * ShoppingListIngredientエンティティにデータを保存
//		 */
//
//		// shoppingListFormに入っている材料IdのデータををListに代入
//		Iterable<Ingredient> selectedIngredientIds = shoppingListForm.getIngredientIds();
//		// ShoppingListIngredientエンティティに材料Idを保存するためのArrayListを作成
//		List<ShoppingListIngredient> shoppingListIngredients = new ArrayList<>();
//		// 材料Idをセット
//		for (Ingredient ingredient : selectedIngredientIds) {
//			ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient();
//			shoppingListIngredient.setIngredient(ingredient);
//			shoppingListIngredient.setShoppingList(newList);
//			shoppingListIngredients.add(shoppingListIngredient);
//		}
//		// 保存
//		shoppingListIngredientRepository.saveAllAndFlush(shoppingListIngredients);
//
//		/*
//		 * ShoppingListSeasoningエンティティにデータを保存
//		 */
//
//		// shoppingListFormに入っている調味料IdのデータをListに代入
//		List<Long> selectedSeasoningIds = shoppingListForm.getSeasoningIds();
//		// ShoppingListSeasoningエンティティに調味料Idを保存するためのArrayListを作成
//		List<ShoppingListSeasoning> shoppingListSeasonings = new ArrayList<>();
//		// 調味料Idをセット
//		for (Long id : selectedSeasoningIds) {
//			ShoppingListSeasoning shoppingListSeasoning = new ShoppingListSeasoning();
//			shoppingListSeasoning.setSeasoningId(id);
//			shoppingListSeasoning.setShoppingList(newList);
//			shoppingListSeasonings.add(shoppingListSeasoning);
//		}
//		// 保存
//		shoppingListSeasoningRepository.saveAllAndFlush(shoppingListSeasonings);

		/*
		 * ShoppingListとShoppingListIngredient/ShoppingListSeasoningの紐付け→不要だった
		 */
//		newList.setShoppingListIngredients(shoppingListIngredients);
//		newList.setShoppingListSeasonings(shoppingListSeasonings);
//		shoppingListRepository.saveAndFlush(newList);

//		return newList;
//	}
}
