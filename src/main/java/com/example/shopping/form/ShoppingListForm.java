package com.example.shopping.form;

import java.time.LocalDate;
import java.util.List;

import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.entity.Shop;
import com.example.shopping.entity.ShoppingListIngredient;

import lombok.Data;

@Data
public class ShoppingListForm {

	private long shoppingListId;
	
	private long appUserId;
	
	private Shop shop;
	
	private LocalDate createdDate;
	
	private List<Ingredient> ingredients;
	
	private List<Seasoning> seasonings;
	
	//TODO AlwaysBuy/LaterBuyもそれぞれ追加
}
