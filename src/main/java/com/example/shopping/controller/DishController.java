package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.Dish;
import com.example.shopping.repository.DishRepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/dish")
public class DishController {

	@Autowired
	DishRepository dishRepository;

	@GetMapping(value = "/register")
	public String get(@ModelAttribute("Form") Dish dish, Model model) {
		model.addAttribute("title", "料理の登録");
		return "dish/register";
	}

	@PostMapping(value = "/list")
	public String add(@ModelAttribute("Form") Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		List<Dish> list = dishRepository.findAll();
		model.addAttribute("data", list);
		return "dish/list";
	}
}