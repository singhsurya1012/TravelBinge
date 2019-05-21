package com.travelbinge.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelbinge.repository.UserDAO;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDAO;
	
	public Map<String, String> checkEmailId(String emailId) {
		
		Map<String, String> response = new HashMap<>();
		
		if (StringUtils.isEmpty(emailId) ) {
			
			response.put("Status", "Failure");
			response.put("Message", "Email Id cannot be null or empty");
			return response;
		}
		//Validate emailId;
		boolean valid = EmailValidator.getInstance().isValid(emailId.trim());
		
		if(!valid) {
			
			response.put("Status", "Failure");
			response.put("Message", "Please provide valid Email Id");
			return response;
		}
		
		
		if(userDAO.emailIdExists(emailId.trim())) {
			
			response.put("Status", "Failure");
			response.put("Message", "Email Id already in use");
			
		}else {
			
			response.put("Status", "Success");
			response.put("Message", "Email Id does not exist");
		}
		
		return response;
	}

}
