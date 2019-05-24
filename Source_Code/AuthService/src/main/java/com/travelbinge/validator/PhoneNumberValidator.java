package com.travelbinge.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, Long>{

	@Override
	public boolean isValid(Long number, ConstraintValidatorContext context) {
		String s = number.toString();
		return s!=null && s.length()>7 && s.length()<12;
	}

}