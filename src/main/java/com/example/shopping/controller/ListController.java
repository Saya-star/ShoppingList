package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	//料理を選択する画面の表示
	@GetMapping(value="/select1")
	public String getPage(Dish dish, Model model) {
		model.addAttribute("title","買い物リストを作る①");
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "shoppinglist/select1";
	}
	
//	//選択された料理の送信
//	@PostMapping(value="/select1")
//	public String sendDish() {
//		
//		return "select2";
//	}
}
