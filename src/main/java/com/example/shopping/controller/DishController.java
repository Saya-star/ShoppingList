package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.shopping.entity.Dish;
import com.example.shopping.repository.DishRepository;

import org.springframework.ui.Model;

@Controller
//@RequestMapping(value = "/dish")
public class DishController {

	@Autowired
	DishRepository dishRepository;

	@RequestMapping(method=RequestMethod.GET,value = "/dish")//変更
	public String get(@ModelAttribute("Form") Dish dish, Model model) {
		model.addAttribute("title", "料理の登録");
		// あとでサービスクラスに書きかえる
		List<Dish> list = dishRepository.findAll();
		model.addAttribute("data", list);
		return "dish";
	}

	@RequestMapping(method=RequestMethod.POST,value = "/dish-details")
	public String add(@ModelAttribute("Form") Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		List<Dish> list = dishRepository.findAll();// 変更点
		model.addAttribute("data", list);// 変更点
		System.out.println("add");// あとで消す
		return "dish-details";// 変更点
	}
}