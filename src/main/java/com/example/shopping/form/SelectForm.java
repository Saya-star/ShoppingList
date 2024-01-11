package com.example.shopping.form;

import java.util.List;

import lombok.Data;

@Data
public class SelectForm {

	private Long ingredientId;
	
	private List<Long> ingredientIds;
	
	private Long seasoningId;
	
	private List<Long> seasoningIds;
	
	private Long alwaysBuyId;
	
	private List<Long> alwaysBuyIds;
}
