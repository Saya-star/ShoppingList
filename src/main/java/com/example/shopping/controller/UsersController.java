package com.example.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.shopping.entity.User;
import com.example.shopping.entity.User.Authority;
import com.example.shopping.form.UserForm;
import com.example.shopping.repository.UserRepository;

@Controller
//@RequestMapping(value = "/users")
public class UsersController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	// ユーザー登録画面へ
	@GetMapping(value = "users/new")
	public String newUser(Model model) {
		model.addAttribute("form", new UserForm());
		return "users/new";
	}

	// ユーザー情報の保存
	@PostMapping(value = "/user")
	public String createUser(@Validated @ModelAttribute("form") UserForm userForm, BindingResult bindingResult,
			Model model, RedirectAttributes ridirectAttributes) {

		System.out.println("newUser");
		String name = userForm.getName();
		String email = userForm.getEmail();
		String password = userForm.getPassword();

		// Emailアドレスのチェック
		if (userRepository.findByUsername(email) != null) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "そのメールアドレスはすでに使用されています。");
			bindingResult.addError(fieldError);
		}

		// バリデーションエラーが発生したときは登録画面を返す
		if (bindingResult.hasErrors()) {
			model.addAttribute("class", "alert-danger");
			model.addAttribute("message", "ユーザー登録に失敗しました。");
			return "users/new";
		}

		User newUser = new User(email, name, passwordEncoder.encode(password), Authority.ROLE_USER);
		userRepository.saveAndFlush(newUser);
		model.addAttribute("hasMessage", true);
		model.addAttribute("class", "alert-info");
		model.addAttribute("message", "ユーザー登録が完了しました。");
		return "sessions/new";
	}

}
