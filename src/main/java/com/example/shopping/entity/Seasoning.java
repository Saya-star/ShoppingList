package com.example.shopping.entity;

import java.time.LocalDate;
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
public class Seasoning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column
	private boolean seasoningDeleted = false;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Seasoning seasoning = (Seasoning) obj;
		return Objects.equals(seasoningName, seasoning.seasoningName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(seasoningName);
	}
}
