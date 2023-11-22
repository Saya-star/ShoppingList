package com.example.shopping.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Seasoning {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long seasoningId;
	
	@Column
	private String seasoningName;
	
	@Column
	private LocalDate createdDate;

	@Column
	private LocalDate updatedDate;
	
	@ManyToOne
	@JoinColumn(name = "dish_id")
	private Dish dish;

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

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}
}
