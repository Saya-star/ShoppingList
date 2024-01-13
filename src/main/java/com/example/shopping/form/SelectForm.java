package com.example.shopping.form;

import java.util.List;

import lombok.Data;

@Data
public class SelectForm {

	private List<Long> ingredientIds;
	
	private List<Long> seasoningIds;
	
	private List<Long> alwaysBuyIds;
}
