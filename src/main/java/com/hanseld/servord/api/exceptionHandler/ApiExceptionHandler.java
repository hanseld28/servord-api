package com.hanseld.servord.api.exceptionHandler;

import java.util.List;
import java.util.Locale;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hanseld.servord.domain.exception.DomainException;
import com.hanseld.servord.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
		
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomain(DomainException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var message = ex.getMessage();
		var datetimeNow = OffsetDateTime.now();
		
		var issue = newIssueNoFields(status.value(), message, datetimeNow);
		
		return handleExceptionInternal(ex, issue, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var message = ex.getMessage();
		var datetimeNow = OffsetDateTime.now();
		
		var issue = newIssueNoFields(status.value(), message, datetimeNow);
		
		return handleExceptionInternal(ex, issue, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Field> issueFields = new ArrayList<Field>();
		var errors = ex.getBindingResult().getAllErrors();
		
		errors.forEach(error -> {
			String fieldName = ((FieldError) error).getField(); 
			String message = messageSource.getMessage(error, Locale.ENGLISH);
			
			issueFields.add(new Field(fieldName, message));
		});
		
		Integer issueStatusCode = status.value();
		String issueTitle = "One or more fields are invalid. "
						  + "Make the correct filling and try again.";
		OffsetDateTime issueDatetimeNow = OffsetDateTime.now();
		
		var issue = new Issue(issueStatusCode, issueTitle, issueDatetimeNow, issueFields);
		
		return super.handleExceptionInternal(ex, issue, headers, status, request);
	}
	
	private Issue newIssueNoFields(Integer status, String message, OffsetDateTime datetimeNow) {
		return new Issue(status, message, datetimeNow);
	}
}
