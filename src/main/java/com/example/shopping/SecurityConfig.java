package com.example.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.example.shopping.filter.FormAuthenticationProvider;
import com.example.shopping.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FormAuthenticationProvider formAuthenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector)
			throws Exception {

		MvcRequestMatcher h2RequestMatcher = new MvcRequestMatcher(introspector, "/**");
		h2RequestMatcher.setServletPath("/h2-console");

		RequestMatcher publicMatchers = new OrRequestMatcher(new AntPathRequestMatcher("/"),
				new AntPathRequestMatcher("/error"), new AntPathRequestMatcher("/h2-console/**"),
				new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/users/new"),
				new AntPathRequestMatcher("/user"), new AntPathRequestMatcher("/css/**"),
				new AntPathRequestMatcher("/images/**"), new AntPathRequestMatcher("/scripts/**"));

		// @formatter:off
		httpSecurity.authorizeHttpRequests(authz -> authz.requestMatchers(publicMatchers)
		.permitAll().anyRequest().authenticated())//antMatchersで指定したパス以外認証する
		.formLogin(login -> login.loginProcessingUrl("/login")//ログイン情報の送信先
		.loginPage("/login") //ログイン画面
		.defaultSuccessUrl("/home") //ログイン成功時の遷移先
		.failureUrl("/login-failure") //ログイン失敗時の遷移先
		.permitAll()) //未ログインでもアクセス可能
		.logout(logout -> logout.logoutSuccessUrl("/logout") //ログアウト成功時の遷移先
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID").permitAll())
		.csrf(csrf -> csrf.ignoringRequestMatchers(h2RequestMatcher))
		.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
		.cors(cors -> cors.disable());
		// @formatter:on

		return httpSecurity.build();
	}

	public FormAuthenticationProvider userDetailsService() {
		return this.formAuthenticationProvider;
	}

	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(formAuthenticationProvider);
		return authenticationManagerBuilder.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
