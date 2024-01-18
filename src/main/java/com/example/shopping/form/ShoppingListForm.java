package com.example.shopping.form;

import java.time.LocalDate;
import java.util.List;

import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.entity.ShoppingListIngredient;

import lombok.Data;

@Data
public class ShoppingListForm {

	private long shoppingListId;
	
	private long appUserId;
	
	private long shopId;
	
	private LocalDate createdDate;
	
	private List<Ingredient> ingredientIds;
	
	private List<Seasoning> seasoningIds;
	
	//TODO AlwaysBuy/LaterBuyもそれぞれ追加
}
