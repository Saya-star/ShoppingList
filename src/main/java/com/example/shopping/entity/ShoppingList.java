package com.example.shopping.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long shoppingListId;
	
	@Column
	private long appUserId;
	
	@Column
	private long shopId;
	
	@Column
	private LocalDate createdDate;
	
	@Column
	private boolean shoppingListDeleted = false;
	
}
