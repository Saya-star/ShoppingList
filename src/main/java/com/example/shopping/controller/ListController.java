package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shopping.entity.Dish;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.service.DishService;

@Controller
@RequestMapping(value="/shoppinglist")
public class ListController {
	
	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	DishService dishService;

	@GetMapping(value="/select1")
	public String getPage(Dish dish, Model model) {
		model.addAttribute("title","title");
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "shoppinglist/select1";
	}
}
