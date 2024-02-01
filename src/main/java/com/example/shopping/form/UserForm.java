package com.example.shopping.form;


import lombok.Data;

@Data
//@PasswordEquals
public class UserForm {

	private String name;
	
	private String email;
	
	private String password;
	
	private String passwordConfirmation;
}
