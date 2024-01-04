package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.repository.AlwaysBuyRepository;
import com.example.shopping.service.AlwaysBuyService;

import jakarta.transaction.Transactional;

//いつも買うものリストのページのコントローラー
@Controller
@RequestMapping(value = "/alwaysBuy")
public class AlwaysBuyController {

	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	@Autowired
	AlwaysBuyService alwaysBuyService;

	// 店名の一覧表示
	@GetMapping
	public String get(@ModelAttribute("formModel") AlwaysBuy alwaysBuy, Model model) {
		model.addAttribute("title", "いつも買うものリスト");
		List<AlwaysBuy> list = alwaysBuyService.get(alwaysBuy);
		model.addAttribute("data", list);
		return "alwaysBuy";
	}

	// いつも買うものを追加
	@PostMapping
	@Transactional
	public String add(@ModelAttribute("formModel") AlwaysBuy alwaysBuy, Model model) {
		alwaysBuyService.add(alwaysBuy);
		return "redirect:/alwaysBuy";
	}

	// いつも買うものの削除
	@PostMapping(value = "/delete")
	public String delete(@RequestParam("id") long id) {
		alwaysBuyService.delete(id);
		return "redirect:/alwaysBuy";
	}
}
