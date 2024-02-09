package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Shop;
import com.example.shopping.entity.UserInf;
import com.example.shopping.repository.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;

	// Shopのリストを取得
	public List<Shop> get(Shop shop, Principal principal) {
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return shopRepository.findAllByUserId(user.getUserId());
	}

	// お店の登録
	public void add(Shop shop, Principal principal) {
		Shop shopEntity = new Shop();
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		shopEntity.setUserId(user.getUserId());
		shopEntity.setShopName(shop.getShopName());
		LocalDate createdDate = LocalDate.now();
		shopEntity.setCreatedDate(createdDate);
		shopRepository.saveAndFlush(shopEntity);
	}

	// お店の削除
	public void delete(long id) {
		Optional<Shop> findShop = shopRepository.findById(id);
		if (findShop.isPresent()) {
			findShop.get().setDeleted(true);
		}
		shopRepository.saveAndFlush(findShop.get());
	}
}
