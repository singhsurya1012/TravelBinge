package com.travelbinge.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {


	@Value("${application-name}") String appName;

	@GetMapping("/welcome")
	public String showLuckyWord() {
		return "Welcome to " + appName;
	}
}
