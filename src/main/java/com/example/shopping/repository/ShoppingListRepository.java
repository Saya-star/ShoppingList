package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.ShoppingList;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

	List<ShoppingList> findAllByUserId(Long userId);
}
