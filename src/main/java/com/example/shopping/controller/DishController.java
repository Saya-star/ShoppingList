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

import com.example.shopping.entity.Dish;
import com.example.shopping.repository.DishRepository;
import com.example.shopping.service.DishService;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/dish")
public class DishController {

	@Autowired
	DishRepository dishRepository;

	@Autowired
	DishService dishService;
	
	// 料理一覧画面の表示
	@GetMapping(value = "/list")
	public String getList(Dish dish,Model model) {
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "dish/list";
	}

	// 料理登録画面の表示
	@GetMapping(value = "/register")
	public String register(@ModelAttribute("Form") Dish dish, Model model) {
		model.addAttribute("title", "料理の登録");
		return "dish/register";
	}

	// 料理の登録
	@PostMapping(value = "/list")
	public String add(@ModelAttribute("Form") Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		List<Dish> list = dishService.getList(dish);
		model.addAttribute("data", list);
		return "dish/list";
	}

	// 登録した料理の詳細表示
	@GetMapping(value = "detail/{id}")
	public String getDetail(@PathVariable("id") long id, Dish dish, Model model) {
		model.addAttribute("title", "料理の詳細");
		Optional<Dish> data = dishService.findDish(id);
		model.addAttribute("detail", data.get());
		return "dish/detail";
	}

	// 料理の内容の編集画面
	@GetMapping(value = "edit/{id}")
	public String edit(@PathVariable("id") long id, @ModelAttribute("Form") Dish dish, Model model) {
		model.addAttribute("title", "料理の編集");
		Optional<Dish> data = dishService.findDish(id);
		model.addAttribute("Form", data.get());
		return "dish/edit";
	}

	// 編集内容を登録
	@PostMapping(value = "edit")
	public String update(@ModelAttribute Dish dish, Model model) {
		dishRepository.saveAndFlush(dish);
		return "redirect:/dish/list";
	}

	// 削除メソッド
	@PostMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		dishRepository.deleteById(id);
		return "redirect:/dish/list";
	}
}