package com.example.shopping.service;

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
import com.example.shopping.form.SelectForm;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.repository.IngredientRepository;
import com.example.shopping.repository.SeasoningRepository;

@Service
public class ShoppingListService {

	@Autowired
	DishRepository dishRepository;

	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	SeasoningRepository seasoningRepository;

	// 選択された料理の材料・調味料と、いつも買うものリスト、あとで買うものリストの表示
	public void getItems(@RequestParam(value = "dishIds", required = false) Long[] dishIds, Model model) {

		List<Ingredient> ingredientList = new ArrayList<>();
		List<Seasoning> seasoningList = new ArrayList<>();

		//選択された料理から材料と調味料を検索
		for (int i = 0; i < dishIds.length; i++) {
			// dishIdで選択された料理を検索
			Optional<Dish> selectedDish = dishRepository.findById(dishIds[i]);

			// 材料を検索し、ingredientListに追加
			List<Ingredient> selectedDishIngredient = selectedDish.get().getIngredient();
			ingredientList.addAll(selectedDishIngredient);

			// 調味料を検索し、seasoningListに追加
			List<Seasoning> selectedDishSeasoning = selectedDish.get().getSeasoning();
			seasoningList.addAll(selectedDishSeasoning);
		}

		model.addAttribute("ingredientList", ingredientList);
		model.addAttribute("seasoningList", seasoningList);

		// いつも買うものリストをalwaysBuyListに追加
		List<AlwaysBuy> alwaysBuyList = alwaysBuyRepository.findAll();
		model.addAttribute("alwaysBuy", alwaysBuyList);
		// あとで買うものをリストに詰める //あとで買うものリストを作ってから着手

	}

	public void selectItems(@ModelAttribute SelectForm selectForm, Model model) {
		// 選択された材料Idから材料名を検索し、Listに登録　//分量が無かった
		List<String> ingredientNamesList = new ArrayList<>();
		for (Long id : selectForm.getIngredientIds()) {
			Optional<Ingredient> selectedIngredient = ingredientRepository.findById(id);
			ingredientNamesList.add(selectedIngredient.get().getIngredientName());
		}
		model.addAttribute("ingredientNames", ingredientNamesList);

		// 選択された調味料Idから調味料名を検索し、Listに登録
		List<String> seasoningNamesList = new ArrayList<>();
		for (Long id : selectForm.getSeasoningIds()) {
			Optional<Seasoning> selectedSeasoning = seasoningRepository.findById(id);
			seasoningNamesList.add(selectedSeasoning.get().getSeasoningName());
		}
		model.addAttribute("seasoningNames", seasoningNamesList);

		// いつも買うものリストから選択されたものをListに登録

		// あとで買うものリストから選択されたものをListに登録

	}
}
