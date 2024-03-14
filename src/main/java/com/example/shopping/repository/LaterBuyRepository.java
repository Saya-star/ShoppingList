package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.LaterBuy;

@Repository
public interface LaterBuyRepository extends JpaRepository<LaterBuy, Long>{

	List<LaterBuy> findAllByUserId(Long userId);
}
