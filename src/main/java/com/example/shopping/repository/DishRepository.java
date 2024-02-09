package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository <Dish, Long> {

	List<Dish> findAllByUserId(Long userId);
}
