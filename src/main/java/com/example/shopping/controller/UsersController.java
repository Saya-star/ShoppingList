package com.example.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

	@GetMapping(value = "/new")
	public String newUser(Model model) {
		return "users/new";
	}
}
