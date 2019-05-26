package com.travelbinge.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelbinge.model.ApiResponse;

import lombok.Getter;
import lombok.Setter;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final String SIGN_IN_URL ="/auth/signIn";

	private AuthenticationManager authManager;

	private JWTConfig jwtConfig;

	private TokenBuilder tokenBuilder;

	private ObjectMapper mapper = new ObjectMapper();


	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JWTConfig jwtConfig,
			TokenBuilder tokenBuilder, ObjectMapper mapper) {
		this.authManager = authManager;
		this.jwtConfig = jwtConfig;
		this.tokenBuilder = tokenBuilder;
		this.mapper = mapper;

		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SIGN_IN_URL, "POST"));
	}





	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			System.out.println("Authenticating User");

			// 1. Get credentials from request
			UserCred credentials = mapper.readValue(request.getInputStream(), UserCred.class);

			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					credentials.getUsername().trim(), credentials.getPwd().trim(), Collections.emptyList());

			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);

		} catch (IOException e) {
			System.out.println("Error while authenticating");
			throw new RuntimeException(e);
		}
	}

	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		System.out.println("User Authenticated Successfully");
		System.out.println(auth.getName());
		System.out.println(auth.toString());

		System.out.println("Building Token");
		String token = tokenBuilder.buildJWTToken(auth);
		System.out.println(token);

		// Add token to header
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);

		//Setting Security Context
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);

		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		//Setting success Response
		ApiResponse successResposne = new ApiResponse();
		successResposne.setStatus(HttpStatus.OK);
		successResposne.setMessage("Users logged in successfuly");

		mapper.writeValue(response.getWriter(), successResposne);

	}


	// Upon unsuccessful authentication
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {

		System.out.println("User Authenticated Failed");

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		ApiResponse failureResponse = new ApiResponse();
		failureResponse.setStatus(HttpStatus.UNAUTHORIZED);
		failureResponse.setError(e.getMessage());
		failureResponse.setMessage("Authentication failed");

		if(e instanceof BadCredentialsException) {
			failureResponse.setMessage("Invalid Username or Password");
		} 

		mapper.writeValue(response.getWriter(), failureResponse);

		SecurityContextHolder.clearContext();
	}


}


@Getter @Setter
class UserCred {

	@Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Username can contain only alphanumeric character - _")
	@Size(min = 3, max = 50, message = "Username must be between 5 and 25 characters")
	private String username;

	private String pwd;




}
