package com.example.shopping.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.AlwaysBuy;
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
	public void add(AlwaysBuy alwaysBuy) {
		LocalDate createdDate = LocalDate.now();
		alwaysBuy.setCreatedDate(createdDate);
		alwaysBuyRepository.saveAndFlush(alwaysBuy);
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
