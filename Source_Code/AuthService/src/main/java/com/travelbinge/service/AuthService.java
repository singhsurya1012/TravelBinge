package com.travelbinge.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelbinge.repository.AuthDAO;

@Service
public class AuthService {
	
	@Autowired
	AuthDAO authDAO;
	
	public Map<String, String> checkEmailId(String emailId) {
		
		Map<String, String> response = new HashMap<>();
		
		if(authDAO.emailIdExists(emailId.trim())) {
			
			response.put("status", "failure");
			response.put("message", "Email Id already in use");
			
		}else {
			
			response.put("status", "success");
			response.put("message", "Email Id does not exist");
		}
		
		return response;
	}

}
