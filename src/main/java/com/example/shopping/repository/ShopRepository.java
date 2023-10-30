package com.example.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

	//削除メソッド
	public void deleteByShopId(long shopId);
	
}
