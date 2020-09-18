package kr.or.ddit.validate.rule;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern; 

@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Pattern(regexp="\\d{2,3}-\\d{3,4}-\\d{4}")
@Constraint(validatedBy= {})
@ReportAsSingleViolation
public @interface TelNumber {
	String message() default "{kr.or.ddit.validate.rule.TelNumber.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}














