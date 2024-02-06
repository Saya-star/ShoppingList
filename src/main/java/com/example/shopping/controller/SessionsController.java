package com.example.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionsController {

	// ログイン画面の表示
	@GetMapping(value = "/login")
	public String index() {
		return "sessions/new";
	}

	// ログイン失敗時
	@GetMapping(value = "/login-failure")
	public String loginFailure(Model model) {
		model.addAttribute("hasMessage", true);
		model.addAttribute("class", "alert-danger");
		model.addAttribute("message", "Emailアドレスまたはパスワードに誤りがあります");
		return "sessions/new";
	}

	// ログアウト
	@GetMapping(value = "/logout")
	public String logoutComplete(Model model) {
		model.addAttribute("hasMessage", true);
		model.addAttribute("class", "alert-info");
		model.addAttribute("message", "ログアウトしました");
		return "index.html";
	}
}
