package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository <Dish, Long> {

}
