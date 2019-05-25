package com.travelbinge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travelbinge.model.ApiResponse;
import com.travelbinge.model.TBUser;
import com.travelbinge.repository.AuthDAO;

@Service
public class AuthService implements UserDetailsService{

	@Autowired
	AuthDAO authDAO;

	@Autowired
	PasswordEncoder passwordEncoder;

	public ApiResponse checkUsername(String username) {
		
		ApiResponse response = new ApiResponse();

		if(authDAO.usernameExists(username.trim())) {

			response.setStatus(HttpStatus.IM_USED);
			response.setMessage("Username already in use");

		}else {

			response.setStatus(HttpStatus.OK);
			response.setMessage("Username does not exist");
		}

		return response;
	}

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

	public ApiResponse signUp(TBUser user) {
		ApiResponse response = new ApiResponse();

		if(authDAO.emailIdExists(user.getEmailId().trim())) {

			response.setStatus(HttpStatus.IM_USED);
			response.setMessage("Email Id already in use");
			return response;
		}
		
		if(authDAO.usernameExists(user.getUsername().trim())) {

			response.setStatus(HttpStatus.IM_USED);
			response.setMessage("Username already in use");
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("Authentication user with username " + username);
		return authDAO.loadUserByUsername(username.trim());
		
	}



}
