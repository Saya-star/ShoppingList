package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.entity.ShoppingList;
import com.example.shopping.entity.ShoppingListAlwaysBuy;
import com.example.shopping.entity.ShoppingListIngredient;
import com.example.shopping.entity.ShoppingListSeasoning;
import com.example.shopping.entity.UserInf;
import com.example.shopping.form.ShoppingListForm;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.SeasoningRepository;
import com.example.shopping.repository.ShoppingListAlwaysBuyRepository;
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
	
	@Autowired
	ShoppingListAlwaysBuyRepository shoppingListAlwaysBuyRepository;
	
	//選択された料理の材料を検索
	public List<Ingredient> findIngredient(Long[] dishIds){
		List<Ingredient> ingredientList = new ArrayList<>();
		for (int i = 0; i < dishIds.length; i++) {
			// dishIdで選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);
			// 選択された料理に必要な材料を検索し、ingredientListに追加
			List<Ingredient> selectedDishIngredient = selectedDish.get().getIngredient();
			ingredientList.addAll(selectedDishIngredient);
		}
		return ingredientList;
	}
	
	//選択された料理の調味料を検索
	public List<Seasoning> findSeasoning(Long[] dishIds){
		List<Seasoning> seasoningList = new ArrayList<>();
		for (int i = 0; i < dishIds.length; i++) {
			// dishIdで選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);
			// 選択された料理に必要な調味料を検索し、seasoningListに追加
			List<Seasoning> selectedDishSeasoning = selectedDish.get().getSeasoning();
			seasoningList.addAll(selectedDishSeasoning);
		}
		return seasoningList;
	}

	//買い物リストの作成
	public ShoppingList createShoppingList(ShoppingListForm shoppingListForm, Principal principal) {

		/*
		 * ShoppingListエンティティにデータを保存
		 */
		
		ShoppingList newList = new ShoppingList();
		
		//ログイン中のユーザー情報を紐付け
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		newList.setUserId(user.getUserId());
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
			// 材料Idを保存するためのArrayList
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
			// 調味料Idを保存するためのArrayList
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
		 * ShoppingListAlwaysBuyエンティティにデータを保存
		 */

		// shoppingListFormに入っている調味料のデータの取り出し
		Optional<List<AlwaysBuy>> selectedAlwaysBuys = Optional.ofNullable(shoppingListForm.getAlwaysBuyList());
		
		if (selectedAlwaysBuys.isPresent()) {
			// いつも買うものIdを保存するためのArrayList
			List<ShoppingListAlwaysBuy> shoppingListAlwaysBuys = new ArrayList<>();
			// いつも買うものIdをセット
			for (AlwaysBuy alwaysBuy : selectedAlwaysBuys.get()) {
				ShoppingListAlwaysBuy shoppingListAlwaysBuy = new ShoppingListAlwaysBuy();
				shoppingListAlwaysBuy.setAlwaysBuy(alwaysBuy);
				shoppingListAlwaysBuy.setShoppingList(newList);
				shoppingListAlwaysBuys.add(shoppingListAlwaysBuy);
			}
			// 保存
			shoppingListAlwaysBuyRepository.saveAllAndFlush(shoppingListAlwaysBuys);
			newList.setShoppingListAlwaysBuys(shoppingListAlwaysBuys);
		}
		
		return newList;
	}
	
	//買い物リストの削除
	public void deleteShoppingList(long shoppingListId) {
		Optional<ShoppingList> deleteShoppingList = shoppingListRepository.findById(shoppingListId);
		if (deleteShoppingList.isPresent()) {
			deleteShoppingList.get().setShoppingListDeleted(true);
			deleteShoppingList.get().getShoppingListIngredients().forEach(shoppingListIngredient -> shoppingListIngredient.setDeleted(true));
			deleteShoppingList.get().getShoppingListSeasonings().forEach(shoppingListSeasoning -> shoppingListSeasoning.setDeleted(true));
			deleteShoppingList.get().getShoppingListAlwaysBuys().forEach(shoppingListAlwaysBuys -> shoppingListAlwaysBuys.setDeleted(true));
			
		}
		shoppingListRepository.saveAndFlush(deleteShoppingList.get());
	}
}
