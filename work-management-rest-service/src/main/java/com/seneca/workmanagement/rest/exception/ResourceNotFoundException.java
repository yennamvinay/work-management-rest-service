package com.seneca.workmanagement.rest.exception;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = -2428883407911804559L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
