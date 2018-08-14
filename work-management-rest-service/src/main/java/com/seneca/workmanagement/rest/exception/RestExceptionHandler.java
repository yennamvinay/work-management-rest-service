package com.seneca.workmanagement.rest.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({ ExecutionException.class })
	public ResponseEntity<Map<String, String>> handleExecutionException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(getErrorResponseMap(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(getErrorResponseMap(ex), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<List<String>> handleValidationException(Exception ex, WebRequest request) {
		ValidationException exception = (ValidationException) ex;
		List<String> errors = new ArrayList<>();
		for (ObjectError error : exception.getErrors()) {
			errors.add(error.getDefaultMessage());
		}
		return new ResponseEntity<>(errors, HttpStatus.PARTIAL_CONTENT);
	}

	private Map<String, String> getErrorResponseMap(Exception ex) {
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("status", "error");
		responseMap.put("message", ex.getMessage());
		return responseMap;
	}
}
