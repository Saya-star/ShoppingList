package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.ShoppingListSeasoning;

@Repository
public interface ShoppingListSeasoningRepository extends JpaRepository<ShoppingListSeasoning, Long>{

}
