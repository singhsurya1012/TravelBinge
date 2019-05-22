package com.travelbinge.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDAO {

	
	@Autowired
	NamedParameterJdbcTemplate mainTemplate;
	
	private static final String CHECK_EMAIL_ID_EXISTS = "select count(*) from user_details t where lower(t.email_id) = lower(:emailId) ";
	
	public Boolean emailIdExists(String emailId) {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("emailId", emailId);
		
		int count = mainTemplate.queryForObject(CHECK_EMAIL_ID_EXISTS, paramSource , Integer.class);
		
		return count==0?false:true;
		
	}
}
