package com.example.shopping.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.shopping.entity.User;
import com.example.shopping.repository.UserRepository;

@Configuration
public class FormAuthenticationProvider implements AuthenticationProvider {

	protected static Logger log = LoggerFactory.getLogger(FormAuthenticationProvider.class);

	@Autowired
	private UserRepository userRepository;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		log.debug("name={}", name);
		log.debug("password={}", password);

		if ("".equals(name) || "".equals(password)) {
			throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");
		}

		User userEntity = userRepository.findByUsername(name);

		if (userEntity == null) {
			throw new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。");
		}
		if (!passwordEncoder.matches(password, userEntity.getPassword())) {
			throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");
		}

		return new UsernamePasswordAuthenticationToken(userEntity, password, userEntity.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
