package com.example.shopping.controller;

import java.util.List;
import java.util.Optional;

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

	//料理登録画面の表示
	@GetMapping(value = "/register")
	public String register(@ModelAttribute("Form") Dish dish, Model model) {
		model.addAttribute("title", "料理の登録");
		return "dish/register";
	}

	//料理の登録
	@PostMapping(value = "/list")
	public String add(@ModelAttribute("Form") Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		List<Dish> list = dishRepository.findAll();
		model.addAttribute("data", list);
		return "dish/list";
	}
	
	//登録した料理の詳細表示
	@GetMapping(value = "detail/{id}")
	public String getDetail(@PathVariable("id") long id,Dish dish,Model model) {
		System.out.println("detail");
		model.addAttribute("title","料理の詳細");
		Optional<Dish> data = dishRepository.findById(id);
		model.addAttribute("detail",data.get());//データを取り出し"form"に代入
		return "dish/detail";
	}
	
}