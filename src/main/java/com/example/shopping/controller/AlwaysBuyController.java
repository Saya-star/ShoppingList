package com.example.shopping.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.service.AlwaysBuyService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

//いつも買うものリストのページのコントローラー
@Controller
@RequestMapping(value = "/alwaysBuy")
@RequiredArgsConstructor
public class AlwaysBuyController {

	private final AlwaysBuyService alwaysBuyService;

	// いつも買うものの一覧表示
	@GetMapping
	public String get(@ModelAttribute("form") AlwaysBuy alwaysBuy, Model model, Principal principal) {
		List<AlwaysBuy> alwaysBuyList = alwaysBuyService.get(alwaysBuy, principal);
		model.addAttribute("data", alwaysBuyList);
		return "alwaysBuy";
	}

	// いつも買うものをDBに保存
	@PostMapping
	public String add(@ModelAttribute("form") AlwaysBuy alwaysBuy, Principal principal) {
		alwaysBuyService.add(alwaysBuy, principal);
		return "redirect:/alwaysBuy";
	}

	// いつも買うものを削除
	@PostMapping(value = "/delete")
	public String delete(@RequestParam("id") long id) {
		alwaysBuyService.delete(id);
		return "redirect:/alwaysBuy";
	}
}
