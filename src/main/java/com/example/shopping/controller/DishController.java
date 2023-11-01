package com.example.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value = "/dish")
public class DishController {
	
	@GetMapping
	public String get(Model model) {
		return "dish";
	}
}