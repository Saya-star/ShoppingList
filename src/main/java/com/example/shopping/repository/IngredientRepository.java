package com.example.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Dish;
import com.example.shopping.entity.Ingredient;
import com.example.shopping.enums.IngredientType;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	public Optional<Ingredient> findByDish(Dish dish);
}
