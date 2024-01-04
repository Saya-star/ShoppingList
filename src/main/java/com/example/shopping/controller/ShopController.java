package com.example.shopping.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.Shop;
import com.example.shopping.repository.ShopRepository;
import com.example.shopping.service.ShopService;

import jakarta.transaction.Transactional;

//お店リストのページのコントローラー
@Controller
@RequestMapping(value = "/shop")
public class ShopController {

	@Autowired
	ShopRepository shopRepository;

	@Autowired
	ShopService shopService;

	// 店名の一覧の表示
	@GetMapping
	public String get(@ModelAttribute("formModel") Shop shop, Model model) {
		model.addAttribute("title", "お店リスト");
		List<Shop> list = shopService.get(shop);
		model.addAttribute("data", list);
		return "shop";
	}

	// リストに店名を追加するメソッド
	@PostMapping
	@Transactional // いる？
	public String add(@ModelAttribute("formModel") Shop shop, Model model) {
		shopRepository.saveAndFlush(shop);
		return "redirect:/shop";
	}

	// 店名の削除
	@PostMapping(value = "/delete")
	public String delete(@RequestParam("id") long id) {
		Optional<Shop> deleteShop = shopService.find(id);
		if (deleteShop.isPresent()) {
			deleteShop.get().setDeleted(true);
		}
		shopRepository.saveAndFlush(deleteShop.get());
		return "redirect:/shop";
	}
}
