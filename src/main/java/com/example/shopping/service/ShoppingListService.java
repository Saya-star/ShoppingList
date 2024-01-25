package com.example.shopping.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

	public ShoppingList createShoppingList(ShoppingListForm shoppingListForm) {

		/*
		 * ShoppingListエンティティにデータを保存
		 */

		ShoppingList newList = new ShoppingList();
		// 買い物リストにお店Idを登録
		newList.setShop(shoppingListForm.getShop());
		// 日時を登録
		LocalDate createdDate = LocalDate.now();
		newList.setCreatedDate(createdDate);
		// 保存
		shoppingListRepository.saveAndFlush(newList);

		/*
		 * ShoppingListIngredientエンティティにデータを保存
		 */
		// shoppingListFormに入っている材料のデータの取り出し
		Optional<List<Ingredient>> selectedIngredients = Optional.ofNullable(shoppingListForm.getIngredientList());
		
		if (selectedIngredients.isPresent()) {
			// ShoppingListIngredientエンティティに材料Idを保存するためのArrayListを作成
			List<ShoppingListIngredient> shoppingListIngredients = new ArrayList<>();
			// 材料Idをセット
			for (Ingredient ingredient : selectedIngredients.get()) {
				ShoppingListIngredient shoppingListIngredient = new ShoppingListIngredient();
				shoppingListIngredient.setIngredient(ingredient);
				shoppingListIngredient.setShoppingList(newList);
				shoppingListIngredients.add(shoppingListIngredient);
			}
			// 保存
			shoppingListIngredientRepository.saveAllAndFlush(shoppingListIngredients);
			newList.setShoppingListIngredients(shoppingListIngredients);
			
		}

		/*
		 * ShoppingListSeasoningエンティティにデータを保存
		 */

		// shoppingListFormに入っている調味料のデータの取り出し
		Optional<List<Seasoning>> selectedSeasonings = Optional.ofNullable(shoppingListForm.getSeasoningList());
		
		if (selectedSeasonings.isPresent()) {
			// ShoppingListSeasoningエンティティに調味料Idを保存するためのArrayListを作成
			List<ShoppingListSeasoning> shoppingListSeasonings = new ArrayList<>();
			// 調味料Idをセット
			for (Seasoning seasoning : selectedSeasonings.get()) {
				ShoppingListSeasoning shoppingListSeasoning = new ShoppingListSeasoning();
				shoppingListSeasoning.setSeasoning(seasoning);
				shoppingListSeasoning.setShoppingList(newList);
				shoppingListSeasonings.add(shoppingListSeasoning);
			}
			// 保存
			shoppingListSeasoningRepository.saveAllAndFlush(shoppingListSeasonings);
			newList.setShoppingListSeasonings(shoppingListSeasonings);
			
		}

		/*
		 * ShoppingListとShoppingListIngredient/ShoppingListSeasoningの紐付け
		 */
//		newList.setShoppingListIngredients(shoppingListIngredients);
//		newList.setShoppingListSeasonings(shoppingListSeasonings);
//		shoppingListRepository.saveAndFlush(newList);

		return newList;
	}
	
	//買い物リストの削除
	public void deleteShoppingList(long shoppingListId) {
		Optional<ShoppingList> deleteShoppingList = shoppingListRepository.findById(shoppingListId);
		if (deleteShoppingList.isPresent()) {
			deleteShoppingList.get().setShoppingListDeleted(true);
			deleteShoppingList.get().getShoppingListIngredients().forEach(shoppingListIngredient -> shoppingListIngredient.setDeleted(true));
			deleteShoppingList.get().getShoppingListSeasonings().forEach(shoppingListSeasoning -> shoppingListSeasoning.setDeleted(true));
		}
		shoppingListRepository.saveAndFlush(deleteShoppingList.get());
	}
}
