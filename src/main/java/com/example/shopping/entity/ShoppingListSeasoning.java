package com.example.shopping.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ShoppingListSeasoning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long shoppingListSeasoningId;
	
	@ManyToOne
	@JoinColumn(name = "seasoningId")
	private Seasoning seasoning;
	
	@ManyToOne
	@JoinColumn(name = "shoppingListId")
	private ShoppingList shoppingList;
	
	@Column
	private boolean deleted = false;
	
	@Override
	public int hashCode() {
	    return Objects.hash(shoppingListSeasoningId);
	}

	
}
