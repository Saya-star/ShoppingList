package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.ShoppingListLaterBuy;

@Repository
public interface ShoppingListLaterBuyRepository extends JpaRepository<ShoppingListLaterBuy, Long>{

}
