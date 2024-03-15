package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.LaterBuy;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.entity.Shop;
import com.example.shopping.entity.ShoppingList;
import com.example.shopping.entity.ShoppingListAlwaysBuy;
import com.example.shopping.entity.ShoppingListIngredient;
import com.example.shopping.entity.ShoppingListLaterBuy;
import com.example.shopping.entity.ShoppingListSeasoning;
import com.example.shopping.entity.UserInf;
import com.example.shopping.form.ShoppingListForm;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.LaterBuyRepository;
import com.example.shopping.repository.SeasoningRepository;
import com.example.shopping.repository.ShoppingListAlwaysBuyRepository;
import com.example.shopping.repository.ShoppingListIngredientRepository;
import com.example.shopping.repository.ShoppingListLaterBuyRepository;
import com.example.shopping.repository.ShoppingListRepository;
import com.example.shopping.repository.ShoppingListSeasoningRepository;
import com.example.shopping.repository.ShopRepository;

@Service
public class ShoppingListService {

	// TODO コンストラクタインジェクションに修正
	@Autowired
	DishRepository dishRepository;

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	@Autowired
	LaterBuyRepository laterBuyRepository;

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

	@Autowired
	ShoppingListLaterBuyRepository shoppingListLaterBuyRepository;

	// 選択された料理の材料を検索
	public List<Ingredient> findIngredient(Long[] dishIds) {
		List<Ingredient> ingredientList = new ArrayList<>();
		for (int i = 0; i < dishIds.length; i++) {
			// dishIdで選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);
			// 選択された料理に必要な材料を検索し、ingredientListに追加
			if (selectedDish.isPresent()) {
				List<Ingredient> selectedDishIngredient = selectedDish.get().getIngredient();
				ingredientList.addAll(selectedDishIngredient);
			}
		}
		// 検索された材料の並び替え（材料種類順）
		ingredientList.sort(Comparator.comparingInt(i -> i.getIngredientType().getTypeId()));
		return ingredientList;
	}

	// 選択された料理の調味料を検索
	public List<Seasoning> findSeasoning(Long[] dishIds) {
		List<Seasoning> seasoningList = new ArrayList<>();
		for (int i = 0; i < dishIds.length; i++) {
			// dishIdで選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);
			// 選択された料理に必要な調味料を検索し、seasoningListに追加
			if (selectedDish.isPresent()) {
				List<Seasoning> selectedDishSeasoning = selectedDish.get().getSeasoning();
				seasoningList.addAll(selectedDishSeasoning);
			}
		}
		// 重複する調味料の削除
		List<Seasoning> dupicateRemoved = seasoningList.stream().distinct().collect(Collectors.toList());
		return dupicateRemoved;
	}

	// ログイン中のユーザーが登録したいつも買うものの検索
	public List<AlwaysBuy> findAlwaysBuy(Principal principal) {
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return alwaysBuyRepository.findAllByUserId(user.getUserId());
	}

	// ログイン中のユーザーが登録したあとで買うものの検索
	public List<LaterBuy> findLaterBuy(Principal principal) {
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return laterBuyRepository.findAllByUserId(user.getUserId());
	}

	// ログイン中のユーザーが登録したお店の検索
	public List<Shop> findShop(Principal principal) {
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return shopRepository.findAllByUserId(user.getUserId());
	}

	// 買い物リストの作成
	public ShoppingList createShoppingList(ShoppingListForm shoppingListForm, Principal principal) {

		/*
		 * ShoppingListエンティティにデータを保存
		 */

		ShoppingList newList = new ShoppingList();

		// ログイン中のユーザー情報を紐付け
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

		/*
		 * ShoppingListLaterBuyエンティティにデータを保存
		 */

		// shoppingListFormに入っている調味料のデータの取り出し
		Optional<List<LaterBuy>> selectedLaterBuys = Optional.ofNullable(shoppingListForm.getLaterBuyList());

		if (selectedLaterBuys.isPresent()) {
			// いつも買うものIdを保存するためのArrayList
			List<ShoppingListLaterBuy> shoppingListLaterBuys = new ArrayList<>();
			// いつも買うものIdをセット
			for (LaterBuy laterBuy : selectedLaterBuys.get()) {
				ShoppingListLaterBuy shoppingListLaterBuy = new ShoppingListLaterBuy();
				shoppingListLaterBuy.setLaterBuy(laterBuy);
				shoppingListLaterBuy.setShoppingList(newList);
				shoppingListLaterBuys.add(shoppingListLaterBuy);
			}
			// 保存
			shoppingListLaterBuyRepository.saveAllAndFlush(shoppingListLaterBuys);
			newList.setShoppingListLaterBuys(shoppingListLaterBuys);
		}

		return newList;
	}

	// 買い物リストの削除
	public void deleteShoppingList(long shoppingListId) {
		Optional<ShoppingList> deleteShoppingList = shoppingListRepository.findById(shoppingListId);
		if (deleteShoppingList.isPresent()) {
			deleteShoppingList.get().setDeleted(true);
			deleteShoppingList.get().getShoppingListIngredients()
					.forEach(shoppingListIngredient -> shoppingListIngredient.setDeleted(true));
			deleteShoppingList.get().getShoppingListSeasonings()
					.forEach(shoppingListSeasoning -> shoppingListSeasoning.setDeleted(true));
			deleteShoppingList.get().getShoppingListAlwaysBuys()
					.forEach(shoppingListAlwaysBuys -> shoppingListAlwaysBuys.setDeleted(true));
			deleteShoppingList.get().getShoppingListLaterBuys()
					.forEach(shoppingListLaterBuys -> shoppingListLaterBuys.setDeleted(true));

		}
		shoppingListRepository.saveAndFlush(deleteShoppingList.get());
	}

	// 買い物リストの検索
	public List<ShoppingList> getShoppingLists(Principal principal) {
		// ログイン中のユーザーが登録した料理のみ一覧表示
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return shoppingListRepository.findAllByUserIdOrderByShoppingListIdDesc(user.getUserId());
	}
}
