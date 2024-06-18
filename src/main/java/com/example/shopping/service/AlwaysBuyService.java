package com.example.shopping.service;

import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.AlwaysBuy;

@Service
public interface AlwaysBuyService {
	
	/**
	 * いつも買うものの一覧取得
	 * @param alwaysBuy
	 * @param principal
	 * @return
	 */
	List <AlwaysBuy> get(AlwaysBuy alwaysBuy, Principal principal);
	
	/**
	 * いつも買うものの登録
	 * @param alwaysBuy
	 * @param principal
	 */
	void add(AlwaysBuy alwaysBuy, Principal principal);
	
	/**
	 * いつも買うものの削除
	 * @param id
	 */
	void delete(long id);
}
