package com.example.shopping.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.LaterBuy;
import com.example.shopping.repository.LaterBuyRepository;

//あとで買うものリストのページのコントローラー
@Controller
@RequestMapping(value = "/laterBuy")
public class LaterBuyController {

	@Autowired
	LaterBuyRepository laterBuyRepository;
	
	@Autowired
	LaterBuyService laterBuyService;
	
	//あとで買うものの一覧表示
	@GetMapping
	public String get(@ModelAttribute("form") LaterBuy laterBuy, Model model, Principal principal) {
		List<LaterBuy> laterBuyList = laterBuyService.get(laterBuy, principal);
		model.addAttribute("data",laterBuyList);
		return "laterBuy";
	}
	
	//あとで買うものをDBに保存
	@PostMapping
	public String add(@ModelAttribute("form") LaterBuy laterBuy, Principal principal) {
		laterBuyService.add(laterBuy, principal);
		return "redirect:/laterBuy";
	}
	
	//いつも買うものを削除
	@PostMapping(value = "/delete")
	public String delete(@RequestParam("id") long id) {
		laterBuyService.delete(id);
		return "redirect:/laterBuy";
	}
}
