package com.travelbinge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.TBUser;
import com.travelbinge.service.UserService;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/getDetails")
	public ApiResponse getUserDetails(@RequestHeader("username") String username) {
		System.out.println("Fetching User Details : " + username);
		return userService.getUserDetails(username);
	}


	
	@PutMapping("/updateDetails")
	public ApiResponse updateUserDetails(@RequestHeader("username") String username,@Valid @RequestBody TBUser user) {
		System.out.println("Updating User Details : " + username);
		user.setUsername(username);
		return userService.updateUserDetails(user);
	}

}
