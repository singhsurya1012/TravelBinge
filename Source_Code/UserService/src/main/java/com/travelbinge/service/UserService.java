package com.travelbinge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.TBUser;
import com.travelbinge.repository.UserDAO;
import com.travelbinge.validator.Validator;

@Service
public class UserService {

	@Autowired
	Validator validator;
	
	@Autowired
	UserDAO userDAO;
	
	
	public ApiResponse getUserDetails(String username) {
		
		if(StringUtils.isEmpty(username)) {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized Access. Please login before accessing", null, null);
		}
		
		return userDAO.getUserDetails(username);
	}


	public ApiResponse updateUserDetails(TBUser user) {
		
		if(StringUtils.isEmpty(user.getUsername())) {
			return  new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized Access. Please login before accessing", null, null);
		}
		
		return userDAO.updateUserDetails(user);
	}

}
