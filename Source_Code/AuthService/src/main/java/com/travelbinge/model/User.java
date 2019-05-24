package com.travelbinge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.travelbinge.validator.AuthCodeConstaint;
import com.travelbinge.validator.PhoneNumberConstraint;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "user_details")
@Setter @Getter 
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
	@NotBlank(message = "Email Id is mandatory")
	@Email(message = "Email Id should be valid")
	@Size(max = 100, message = "Email Id should not be greater than 100 characters")
	private String emailId;

	@Column(name = "phone_number")
	@PhoneNumberConstraint
	private long phoneNumber;

	@Column(name = "auth_code")
	private String authCode;

	@Column(name = "state")
	@NotBlank(message = "State is mandatory")
	@Size(max = 100, message = "State should not be greater than 100 characters")
	private String state;

	@Column(name = "country")
	@NotBlank(message = "Country is mandatory")
	@Size(max = 100, message = "Country should not be greater than 100 characters")
	private String country;

	@Column(name = "is_active")
	private String isActive;
	
	@Transient
	@AuthCodeConstaint
	private String passCode;
	
	@Transient
	@AuthCodeConstaint
	private String confirmPassCode;
	
	@Transient
	@AssertTrue(message = "Password Mismatch")
	private boolean passwordMatch;
	
	public void setPasswordMatch(boolean passwordMatch) {
		this.passwordMatch = (this.passCode.equals(this.confirmPassCode));
	}

	
}
