package com.project.webstore.errorhandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	ErrorResponseBodyGenerator errorResponseBodyGenerator = new ErrorResponseBodyGenerator();

	// sending the errors in the response body for missing arguments in request body
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = exception.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getDefaultMessage()).collect(Collectors.toList());

		Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
				status.value() + " " + status.name(), errors);

		return new ResponseEntity<Object>(body, headers, status);
	}

	// sending the error message in response body for incorrect request body
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "incorrect JSON format in request body or request body cannot be empty";

		List<String> errors = new ArrayList<String>();
		errors.add(errorMessage);

		Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
				status.value() + " " + status.name(), errors);

		return new ResponseEntity<Object>(body, headers, status);
	}

	// sending the error message in response body for method not allowed
	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "" + exception.getMessage() + " to the " + request.getDescription(false);

		List<String> errors = new ArrayList<String>();
		errors.add(errorMessage);

		Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
				status.value() + " " + status.name(), errors);

		return new ResponseEntity<Object>(body, headers, status);
	}

	// sending the error message in response body for incorrect request media type
	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "" + ex.getMessage();

		List<String> errors = new ArrayList<String>();
		errors.add(errorMessage);

		Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
				status.value() + " " + status.name(), errors);

		return new ResponseEntity<Object>(body, headers, status);
	}
}
