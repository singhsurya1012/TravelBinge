package com.travelbinge.controller;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.TBUser;
import com.travelbinge.service.AuthService;

@RestController
@Validated
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@GetMapping("/checkEmailId")
	public ApiResponse checkEmailId(@RequestParam @Email(message = "Please provide a valid Email Id") String emailId) {

		return authService.checkEmailId(emailId);
	}

	@GetMapping("/checkUsername")
	public ApiResponse checkUsername(@RequestParam @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Username can contain only alphanumeric character - _")
			@Size(min = 3, max = 50, message = "Username must be between 5 and 25 characters") String username) {

		return authService.checkUsername(username);
	}


	@PostMapping("/signUp")
	public ApiResponse signUp(@Valid @RequestBody  TBUser user) {

		return authService.signUp(user);
	}


}
