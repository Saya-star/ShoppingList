package com.example.shopping.entity;

import java.time.LocalDate;
import com.example.shopping.enums.IngredientType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long ingredientId;

	@Column
	private String ingredientName;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private IngredientType ingredientType;

	@Column
	private String quantity;

	@Column
	private LocalDate createdDate;

	@Column
	private LocalDate updatedDate;

	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

	@Column
	private boolean ingredientDeleted = false;
	
	public int getTypeId() {
		return ingredientType.getTypeId();
	}

}