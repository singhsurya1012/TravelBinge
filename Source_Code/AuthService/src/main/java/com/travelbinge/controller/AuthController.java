package com.travelbinge.controller;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.User;
import com.travelbinge.service.AuthService;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	@Autowired
	AuthService authService;

	@GetMapping("/checkEmailId")
	public ApiResponse checkEmailId(@RequestParam @Email(message = "Please provide a valid Email Id") String emailId) {
		
		return authService.checkEmailId(emailId);
	}
	
	@PostMapping("/signUp")
	public ApiResponse signUp(@Valid @RequestBody  User user) {
		
		return authService.signUp(user);
	}
	
	@PostMapping("/signIn")
	public ApiResponse signIn(@RequestBody  User user) {
		
		return authService.signIn(user);
	}

}
