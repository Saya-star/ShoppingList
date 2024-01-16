package com.example.shopping.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ShoppingListIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long shoppingListIngredientId;
	
//	@Column
//	private Ingredient ingredient;
	
//	@OneToOne(mappedBy="shoppingListIngredient")//Allかな？
//	private Ingredient ingredient;
//	
	@Column //Nameかな？
	private long ingredientId;
//	
//	@Column //必要？
//	private String ingredientName;
//	
//	@Column //必要？
//	private String quantity;
	
	@ManyToOne
	@JoinColumn(name = "shoppingListId")
	private ShoppingList shoppingList;
	
	@Column
	private boolean deleted = false;
	
}
