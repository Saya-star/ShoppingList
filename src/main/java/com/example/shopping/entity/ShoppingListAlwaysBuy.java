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
public class ShoppingListAlwaysBuy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long shoppingListAlwaysBuyId;
	
	@ManyToOne
	@JoinColumn(name = "alwaysBuyId")
	private AlwaysBuy alwaysBuy;
	
	@ManyToOne
	@JoinColumn(name = "shoppingListId")
	private ShoppingList shoppingList;
	
	@Column
	private boolean deleted = false;
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		// 主キーが等しいかどうかを確認
		return Objects.equals(this.shoppingListAlwaysBuyId, ((ShoppingListAlwaysBuy) object).shoppingListAlwaysBuyId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(shoppingListAlwaysBuyId);
	}

}
