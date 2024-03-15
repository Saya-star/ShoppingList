package com.example.shopping.form;

import java.util.List;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.LaterBuy;
import com.example.shopping.entity.Seasoning;

import lombok.Data;

@Data
public class SelectForm {

	private List<Ingredient> ingredients;
	
	private List<Seasoning> seasonings;
	
	private List<AlwaysBuy> alwaysBuys;
	
	private List<LaterBuy> laterBuys;
}
