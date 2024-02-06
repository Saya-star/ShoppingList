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
public class AlwaysBuy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long alwaysBuyId;

	@Column
	private long userId;
	
	@Column
	private String alwaysBuyName;
	
	@Column
	private LocalDate createdDate;
	
	@Column
	private boolean deleted = false;
	
}
