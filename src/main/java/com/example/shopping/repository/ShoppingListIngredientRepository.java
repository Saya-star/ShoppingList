package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.ShoppingListIngredient;

@Repository
public interface ShoppingListIngredientRepository extends JpaRepository<ShoppingListIngredient, Long>{

}
