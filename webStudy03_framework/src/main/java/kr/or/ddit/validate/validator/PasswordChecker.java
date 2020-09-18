package kr.or.ddit.validate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kr.or.ddit.validate.rule.PasswordCheck;

public class PasswordChecker implements ConstraintValidator<PasswordCheck, String>{

	private PasswordCheck constraint;
	
	@Override
	public void initialize(PasswordCheck constraintAnnotation) {
		this.constraint = constraintAnnotation;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		int min = constraint.min();
		int max = constraint.max();
		String regex = constraint.regex();
		int length = value!=null?value.length():-1;
		boolean valid = length>=min && length<=max && value.matches(regex);
		return valid;
	}

}











