package com.travelbinge.controller;

import java.util.Map;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbinge.service.AuthService;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

	@Autowired
	AuthService authService;

	@GetMapping("/checkEmailId")
	public Map<String, String> checkEmailId(@RequestParam @Email(message = "Please provide a valid Email Id") String emailId) {
		
		return authService.checkEmailId(emailId);
	}

}
