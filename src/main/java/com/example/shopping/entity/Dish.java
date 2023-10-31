package com.example.shopping.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
}
