package com.travelbinge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "user_details")
@Setter @Getter 
@ToString
public class TBUser {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "username")
	private String username;

	
	@Column(name = "first_name")
	@Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
	private String firstName;

	@Column(name = "last_name")
	@Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
	private String lastName;

	
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "phone_number")
	private long phoneNumber;
	
	@Column(name = "state")
	@NotBlank(message = "State is mandatory")
	@Size(max = 100, message = "State should not be greater than 100 characters")
	private String state;

	@Column(name = "country")
	@NotBlank(message = "Country is mandatory")
	@Size(max = 100, message = "Country should not be greater than 100 characters")
	private String country;
	

	


}
