package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.entity.Shop;
import com.example.shopping.repository.ShopRepository;

import jakarta.transaction.Transactional;

//お店リストのページのコントローラー
@Controller
public class ShopController {

	@Autowired
	ShopRepository shopRepository;

	//店名の一覧の表示
	@RequestMapping(value = "/shop")
	public String get(@ModelAttribute("formModel") Shop shop, Model model) {
		model.addAttribute("title", "お店リスト");
		List<Shop> list = shopRepository.findAll();
		model.addAttribute("data", list);
		return "shop";
	}

	//リストに店名を追加するメソッド
	@RequestMapping(value = "/shop", method = RequestMethod.POST)
	@Transactional
	public String add(@ModelAttribute("formModel") Shop shop, Model model) {
		shopRepository.saveAndFlush(shop);
		return "redirect:/shop";
	}
	
	//店名の削除メソッド
	@RequestMapping(value = "/shop/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("id") long id) {
		shopRepository.deleteById(id);
		return "redirect:/shop";
	}
}
