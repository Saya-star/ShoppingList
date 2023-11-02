package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shopping.entity.Dish;
import com.example.shopping.repository.DishRepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/dish")
public class DishController {
	
	@Autowired
	DishRepository dishRepository;
	
	@GetMapping
	public String get(@ModelAttribute("Form") Dish dish,Model model) {
		model.addAttribute("title", "料理の登録");
		//あとでサービスクラスに書きかえる
		List<Dish> list = dishRepository.findAll();
		model.addAttribute("data",list);
		return "dish";
	}
	//メモ：材料と分量をそれぞれ表示するには？
	@PostMapping
	public String add(@ModelAttribute("Form")Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		return "redirect:/dish";
	}
}