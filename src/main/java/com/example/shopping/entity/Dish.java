package com.example.shopping.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
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
	
	@OneToMany(mappedBy="dish",cascade = CascadeType.ALL )
	private List<Ingredient> ingredient;
	
	@OneToMany(mappedBy="dish")
	private List<Seasoning> seasoning;
	
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

	public List<Ingredient> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<Ingredient> ingredient) {
		this.ingredient = ingredient;
	}

	public List<Seasoning> getSeasoning() {
		return seasoning;
	}

	public void setSeasoning(List<Seasoning> seasoning) {
		this.seasoning = seasoning;
	}
	
}