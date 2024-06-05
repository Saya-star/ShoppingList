package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.entity.AlwaysBuy;
import com.example.shopping.entity.UserInf;
import com.example.shopping.repository.AlwaysBuyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlwaysBuyServiceImpl implements AlwaysBuyService {

	private final AlwaysBuyRepository alwaysBuyRepository;

	// いつも買うものの一覧を取得
	@Override
	@Transactional
	public List<AlwaysBuy> get(AlwaysBuy alwaysBuy, Principal principal) {
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return alwaysBuyRepository.findAllByUserId(user.getUserId());
	}

	// いつも買うものの登録
	@Override
	@Transactional
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

	// いつも買うものの削除
	@Override
	@Transactional
	public void delete(long id) {
		Optional<AlwaysBuy> findItem = alwaysBuyRepository.findById(id);
		if (findItem.isPresent()) {
			findItem.get().setDeleted(true);
			alwaysBuyRepository.saveAndFlush(findItem.get());
		}
	}
}

