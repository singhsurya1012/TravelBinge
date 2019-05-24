package com.travelbinge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.User;
import com.travelbinge.repository.AuthDAO;

@Service
public class AuthService {

	@Autowired
	AuthDAO authDAO;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	public ApiResponse checkEmailId(String emailId) {

		ApiResponse response = new ApiResponse();

		if(authDAO.emailIdExists(emailId.trim())) {

			response.setStatus(HttpStatus.IM_USED);
			response.setMessage("Email Id already in use");

		}else {

			response.setStatus(HttpStatus.OK);
			response.setMessage("Email Id does not exist");
		}

		return response;
	}

	public ApiResponse signUp(User user) {
		ApiResponse response = new ApiResponse();

		if(authDAO.emailIdExists(user.getEmailId())) {

			response.setStatus(HttpStatus.IM_USED);
			response.setMessage("Email Id already in use");
			return response;
		}
		
		//Encoding the password
		user.setAuthCode(passwordEncoder.encode(user.getPassCode()));
		user.setIsActive("N");
		
		
		if(authDAO.signup(user)) {
			
			response.setStatus(HttpStatus.CREATED);
			response.setMessage("User SignUp Successful");
		}else {
			response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			response.setMessage("User SignUp Failed");
		}
		
		return response;
	}

	public ApiResponse signIn(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
