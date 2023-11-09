package com.example.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Dish;
import com.example.shopping.repository.DishRepository;

//料理の登録ページのコントローラー
@Service
public class DishService {

	@Autowired
	DishRepository dishRepository;
	
	public List<Dish> getList (Dish dish){
		List<Dish> list = dishRepository.findAll();
		return list;
	}
	
	public Optional<Dish> findDish(long id) {
		Optional<Dish> data = dishRepository.findById(id);
		return data;
	}
}
