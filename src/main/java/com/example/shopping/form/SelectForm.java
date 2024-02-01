package com.example.shopping.form;

import java.util.List;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.entity.Seasoning;

import lombok.Data;

@Data
public class SelectForm {

	private List<Ingredient> ingredients;//Longから変更
	
	private List<Seasoning> seasonings;//Longから変更
	
	private List<AlwaysBuy> alwaysBuys;//Longから変更
}
