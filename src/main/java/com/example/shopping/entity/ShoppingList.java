package com.example.shopping.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long shoppingListId;

	@Column
	private long userId;

	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;

	@Column
	private LocalDate createdDate;

	@OneToMany(mappedBy = "shoppingList")
	private List<ShoppingListIngredient> shoppingListIngredients;

	@OneToMany(mappedBy = "shoppingList")
	private List<ShoppingListSeasoning> shoppingListSeasonings;

	@OneToMany(mappedBy = "shoppingList")
	private List<ShoppingListAlwaysBuy> shoppingListAlwaysBuys;

	@Column
	private boolean shoppingListDeleted = false;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		// 主キーが等しいかどうかを確認
		return Objects.equals(this.shoppingListId, ((ShoppingList) object).shoppingListId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(shoppingListId);
	}

}
