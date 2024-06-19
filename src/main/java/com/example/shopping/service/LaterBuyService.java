package com.example.shopping.service;

import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.LaterBuy;

@Service
public interface LaterBuyService {

	/**
	 * あとで買うものの一覧取得
	 * 
	 * @param laterBuy
	 * @param principal
	 * @return
	 */
	List<LaterBuy> get(LaterBuy laterBuy, Principal principal);

	/**
	 * あとで買うものの登録
	 * 
	 * @param laterBuy
	 * @param principal
	 */
	void add(LaterBuy laterBuy, Principal principal);

	/**
	 * あとで買うものの削除
	 * 
	 * @param id
	 */
	void delete(long id);
}
