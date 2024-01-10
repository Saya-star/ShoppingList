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
import lombok.Data;

@Entity
@Data
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@OneToMany(mappedBy="dish",cascade = CascadeType.ALL)
	private List<Seasoning> seasoning;
	
	@Column
	private boolean dishDeleted = false;
	
}