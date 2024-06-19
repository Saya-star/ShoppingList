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
import com.example.shopping.entity.Shop;
import com.example.shopping.service.ShopService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

//お店リストのページのコントローラー
@Controller
@RequestMapping(value = "/shop")
@RequiredArgsConstructor
public class ShopController {

	private final ShopService shopService;

	// 店名の一覧表示
	@GetMapping
	public String get(@ModelAttribute("form") Shop shop, Model model, Principal principal) {
		model.addAttribute("title", "お店リスト");
		List<Shop> shopList = shopService.get(shop, principal);
		model.addAttribute("data", shopList);
		return "shop";
	}

	// 店名を追加
	@PostMapping
	@Transactional
	public String add(@ModelAttribute("form") Shop shop, Model model, Principal principal) {
		shopService.add(shop, principal);
		return "redirect:/shop";
	}

	// 店名の削除
	@PostMapping(value = "/delete")
	public String delete(@RequestParam("id") long id) {
		shopService.delete(id);
		return "redirect:/shop";
	}
}
