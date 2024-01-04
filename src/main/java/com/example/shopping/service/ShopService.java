package com.example.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Shop;
import com.example.shopping.repository.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;

	//Shopのリストを取得
	public List<Shop> get(Shop shop) {
		return shopRepository.findAll();
	}

	// idからShopを検索
	public Optional<Shop> find(long id) {
		return shopRepository.findById(id);
	}
}
