package com.seneca.workmanagement.rest.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 3517520039375867313L;

	private final List<ObjectError> errors;

	public ValidationException(List<ObjectError> errors) {
		this.errors = errors;
	}

	public List<ObjectError> getErrors() {
		return this.errors;
	}
}
