package com.example.shopping.enums;

public enum IngredientType {

	VEGETABLES(1,"野菜"),
	FRUITS(2,"果物"),
	DAIRY_PRODUCT(3,"乳製品"),
	SOY_PRODUCT(4,"大豆製品"),
	SEAFOODS(5,"魚介類"),
	MEAT(6,"肉"),
	OTHER(7,"その他");
	
	private int typeId;
	private String typeName;
	
	private IngredientType(int typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
}
