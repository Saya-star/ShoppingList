package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.UserInf;
import com.example.shopping.repository.AlwaysBuyRepository;

@Service
public class AlwaysBuyService {

	@Autowired
	AlwaysBuyRepository alwaysBuyRepository;

	// いつも買うものの一覧を取得
	public List<AlwaysBuy> get(AlwaysBuy alwaysBuy) {
		return alwaysBuyRepository.findAll();
	}

	// いつも買うものの登録
	public void add(AlwaysBuy alwaysBuy, Principal principal) {
		AlwaysBuy alwaysBuyEntity = new AlwaysBuy();
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		alwaysBuyEntity.setUserId(user.getUserId());
		alwaysBuyEntity.setAlwaysBuyName(alwaysBuy.getAlwaysBuyName());
		LocalDate createdDate = LocalDate.now();
		alwaysBuyEntity.setCreatedDate(createdDate);
		alwaysBuyRepository.saveAndFlush(alwaysBuyEntity);
	}

	// お店の削除
	public void delete(long id) {
		Optional<AlwaysBuy> findItem = alwaysBuyRepository.findById(id);
		if (findItem.isPresent()) {
			findItem.get().setDeleted(true);
		}
		alwaysBuyRepository.saveAndFlush(findItem.get());
	}
}
