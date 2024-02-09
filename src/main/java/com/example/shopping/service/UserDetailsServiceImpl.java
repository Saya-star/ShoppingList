package com.example.shopping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.User;
import com.example.shopping.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	protected static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.debug("username={}", username); //ユーザー名をログ出力

		if (username == null || "".equals(username)) {
			throw new UsernameNotFoundException("Username is empty");
		}
		User user = userRepository.findByUsername(username);
		return user;
	}
}
