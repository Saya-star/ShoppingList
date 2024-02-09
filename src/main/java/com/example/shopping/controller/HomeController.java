package com.example.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//ログイン後のメインページのコントローラー
@Controller
@RequestMapping
public class HomeController {

	@GetMapping(value = "/home")
	public String home(Model model) {
		return "home";
	}
	
	//あとで別のコントローラーへ移動するかも
	//最初に表示されるページ
	@GetMapping(value = "/index")
	public String index() {
		return "index";
	}
}
