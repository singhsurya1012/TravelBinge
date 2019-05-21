package com.travelbinge.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbinge.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/user/checkEmailId")
	public Map<String, String> checkEmailId(@RequestParam(value = "emailId", required = false) String emailId) {

		return userService.checkEmailId(emailId);
	}

}
