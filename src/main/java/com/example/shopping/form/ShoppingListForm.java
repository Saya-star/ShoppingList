package com.example.shopping.form;

import java.time.LocalDate;
import java.util.List;

import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.ShoppingListIngredient;

import lombok.Data;

@Data
public class ShoppingListForm {

	private long shoppingListId;
	
	private long appUserId;
	
	private long shopId;
	
	private LocalDate createdDate;
	
	private List<Long> ingredientIds;
	//private List<ShoppingListIngredient> shoppingListIngredientIds;
	private List<Long> seasoningIds;
	
	//TODO ShoppingListSeasoning/ShoppingListAlwaysBuy/ShoppingListLaterBuyもそれぞれ追加
}
