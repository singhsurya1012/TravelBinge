package com.travelbinge.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelbinge.service.AuthService;

@EnableWebSecurity
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthService authService;

	@Autowired
	private JWTConfig jwtConfig;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenBuilder tokenBuilder;
	
	@Autowired
	private ObjectMapper mapper;;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		// make sure we use stateless session; session won't be used to store user's state.
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		// handle an authorized attempts 
		.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		.and()
		// Add a filter to validate user credentials and add token in the response header

		// What's the authenticationManager()? 
		// An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
		// The filter needs this auth manager to authenticate the user.
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,tokenBuilder,mapper))	
		.authorizeRequests()
		// allow all POST requests 
		.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
		.antMatchers(HttpMethod.GET, jwtConfig.getUri()).permitAll()
		.antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
		.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
		.antMatchers(HttpMethod.GET,"/swagger/**").permitAll()
		.antMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
		.antMatchers(HttpMethod.GET,"/webjars/**").permitAll()
		.antMatchers(HttpMethod.GET,"/v2/**").permitAll()
		// any other requests must be authenticated
		.anyRequest().authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Configuring Authenticationmanager");
		auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
	}
}
