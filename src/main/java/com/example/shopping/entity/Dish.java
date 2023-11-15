package com.example.shopping.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long dishId;

	@Column
	private long appUserId;

	@Column
	private String dishName;

	@Column
	private LocalDate createdDate;

	@Column
	private LocalDate updatedDate;

	// 料理の材料Id
	//@Column
	//private long ingredientId;

	// 料理の材料名
	//@Column
	//private String ingredientName;

	// 材料の分量
	//@Column
	//private String quantity;

	// 調味料のId
	@Column
	private long seasoningId;

	// 調味料名
	@Column
	private String seasoningName;
	
	@OneToMany(mappedBy="dish")
	private List<Ingredient> ingredient;
	
	public long getDishId() {
		return dishId;
	}

	public void setDishId(long dishId) {
		this.dishId = dishId;
	}

	public long getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(long appUserId) {
		this.appUserId = appUserId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	//public long getIngredientId() {
	//	return ingredientId;
	//}

	//public void setIngredientId(long ingredientId) {
	//	this.ingredientId = ingredientId;
	//}

	//public String getIngredientName() {
	//	return ingredientName;
	//}

	//public void setIngredientName(String ingredientName) {
	//	this.ingredientName = ingredientName;
	//}

	//public String getQuantity() {
	//	return quantity;
	//}

	//public void setQuantity(String quantity) {
	//	this.quantity = quantity;
	//}

	public long getSeasoningId() {
		return seasoningId;
	}

	public void setSeasoningId(long seasoningId) {
		this.seasoningId = seasoningId;
	}

	public String getSeasoningName() {
		return seasoningName;
	}

	public void setSeasoningName(String seasoningName) {
		this.seasoningName = seasoningName;
	}

	public List<Ingredient> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<Ingredient> ingredient) {
		this.ingredient = ingredient;
	}
}
