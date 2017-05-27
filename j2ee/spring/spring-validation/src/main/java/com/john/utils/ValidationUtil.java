package com.john.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

public class ValidationUtil {
	
	public static void validateWithException(Validator validator, Object object, Class<?>... groups)
			throws ConstraintViolationException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				sb.append(constraintViolation.getMessage());
			}
			throw new RuntimeException(sb.toString());
		}
	}
}
