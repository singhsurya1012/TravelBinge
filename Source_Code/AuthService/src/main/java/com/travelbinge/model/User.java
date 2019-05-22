package com.travelbinge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "student")
@Getter @Setter 	
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "first_name")
	@Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
	private String firstName;

	@Column(name = "last_name")
	@Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
	private String lastName;

	@Column(name = "email_id")
	@NotNull
	@Email(message = "Email Id should be valid")
	@Max(value = 100, message = "Email Id should not be greater than 100 characters")
	private String emailId;

	@Column(name = "phone_number")
	@NotNull
	private int phoneNumber;

	@Column(name = "auth_code")
	@NotNull
	private String authCode;

	@Column(name = "state")
	@NotNull
	@Max(value = 100, message = "State should not be greater than 100 characters")
	private String state;

	@Column(name = "country")
	@NotNull
	@Max(value = 100, message = "Country should not be greater than 100 characters")
	private String country;

	@Column(name = "is_active")
	@NotNull
	private char isActive;






}
