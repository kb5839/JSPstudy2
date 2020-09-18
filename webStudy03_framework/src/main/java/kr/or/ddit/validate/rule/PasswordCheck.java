package kr.or.ddit.validate.rule;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import kr.or.ddit.validate.validator.PasswordChecker;

@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PasswordChecker.class)
public @interface PasswordCheck {
	int min() default 4;
	int max() default 8;
	String regex() default "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%&*]).*";
	
	String message() default "{kr.or.ddit.validate.rule.PasswordCheck.message}";
	
	Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}












