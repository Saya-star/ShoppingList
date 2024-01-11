package com.example.shopping.form;

import lombok.Data;

@Data
public class SelectForm {

	private Long ingredientId;
	
	private Long[] ingredientIds;
	
	private Long seasoningId;
	
	private Long[] seasoningIds;
	
	private Long alwaysBuyId;
	
	private Long[] alwaysBuyIds;
}
