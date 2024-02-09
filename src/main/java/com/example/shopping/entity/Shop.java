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
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long shopId;

	@Column
	private long userId;
	
	@Column
	private String shopName;
	
	@Column
	private LocalDate createdDate;
	
	@Column
	private boolean deleted = false;
	
}