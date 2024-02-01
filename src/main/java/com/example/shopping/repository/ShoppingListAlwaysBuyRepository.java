package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.ShoppingListAlwaysBuy;

@Repository
public interface ShoppingListAlwaysBuyRepository extends JpaRepository<ShoppingListAlwaysBuy, Long> {

}
