package com.example.shopping.form;

import java.time.LocalDate;
import java.util.List;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;
import com.example.shopping.entity.Shop;
import lombok.Data;

@Data
public class ShoppingListForm {

	private long shoppingListId;
	
	private long appUserId;
	
	private Shop shop;
	
	private LocalDate createdDate;
	
	private List<Ingredient> ingredientList;
	
	private List<Seasoning> seasoningList;
	
	//TODO AlwaysBuy/LaterBuyもそれぞれ追加
	private List<AlwaysBuy> alwaysBuyList;
}
