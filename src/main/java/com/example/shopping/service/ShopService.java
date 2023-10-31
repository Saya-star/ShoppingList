package com.example.shopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.shopping.entity.Shop;
import com.example.shopping.repository.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;
	
	public List<Shop> get(@ModelAttribute("formModel") Shop shop) {
		List<Shop> list = shopRepository.findAll();
		return list;
	}
}
