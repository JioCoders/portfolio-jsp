package com.jiocoders.portfolio.validators;

import com.jiocoders.portfolio.util.Role;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation for Role enum
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface ValidRole {

	String message() default "Invalid role. Must be one of: ADMIN, USER, MEMBER";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}

/**
 * Validator implementation for Role enum
 */
class RoleValidator implements ConstraintValidator<ValidRole, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty()) {
			return true; // Let @NotNull handle null validation
		}
		return Role.isValidRole(value);
	}

}