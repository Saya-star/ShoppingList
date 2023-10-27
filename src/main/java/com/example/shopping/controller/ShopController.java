package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.shopping.entity.Shop;
import com.example.shopping.repository.ShopRepository;

import jakarta.transaction.Transactional;

//お店リストのページのコントローラー
@Controller
public class ShopController {

	@Autowired
	ShopRepository shopRepository;

	@RequestMapping(value = "/shop")
	public String index(@ModelAttribute("formModel") Shop shop, Model model) {
		model.addAttribute("title", "お店リスト");
		List<Shop> list = shopRepository.findAll();
		model.addAttribute("data", list);
		return "shop";
	}

	@RequestMapping(value = "/shop", method = RequestMethod.POST)
	@Transactional
	public String form(@ModelAttribute("formModel") Shop shop, Model model) {
		shopRepository.saveAndFlush(shop);
		return "redirect:/shop";
	}
	
	//deleteメソッドを書きたい
	//@RequestMapping(value = "/shops/delete", method = RequestMethod.POST)
	//@Transactional
	//public String delete(Model model) {
		//repository.deleteById();
		//return "redirect:/shops";
	//}

}
