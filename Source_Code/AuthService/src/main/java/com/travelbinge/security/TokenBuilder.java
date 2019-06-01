package com.travelbinge.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenBuilder {
	
	@Autowired
	JWTConfig jwtConfig;

	
	public String buildJWTToken(Authentication auth) {
		
		if (null == auth.getName() || "".equals(auth.getName().trim())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");
		
		System.out.println("Secret");
		System.out.println(jwtConfig.getSecret());
		
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
				.setSubject(auth.getName().trim())
				.claim("authorities", auth.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();
		return token;
	}

}
