package com.example.shopping.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.entity.LaterBuy;
import com.example.shopping.entity.UserInf;
import com.example.shopping.repository.LaterBuyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LaterBuyServiceImpl implements LaterBuyService {

	private final LaterBuyRepository laterBuyRepository;

	// あとで買うものの一覧を取得
	@Override
	@Transactional
	public List<LaterBuy> get(LaterBuy laterBuy, Principal principal) {
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		return laterBuyRepository.findAllByUserId(user.getUserId());
	}

	// あとで買うものの登録
	@Override
	@Transactional
	public void add(LaterBuy laterBuy, Principal principal) {
		LaterBuy laterBuyEntity = new LaterBuy();
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		laterBuyEntity.setUserId(user.getUserId());
		laterBuyEntity.setLaterBuyName(laterBuy.getLaterBuyName());
		LocalDate createdDate = LocalDate.now();
		laterBuyEntity.setCreatedDate(createdDate);
		laterBuyRepository.saveAndFlush(laterBuyEntity);
	}

	// あとで買うものの削除
	@Override
	@Transactional
	public void delete(long id) {
		Optional<LaterBuy> findItem = laterBuyRepository.findById(id);
		if (findItem.isPresent()) {
			findItem.get().setDeleted(true);
			laterBuyRepository.saveAndFlush(findItem.get());
		}
	}
}
