package com.aim.exception;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class CustomRestControllerAdvice {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto illegalExHandler(IllegalArgumentException e) {
        log.error("error : IllegalArgumentException");
        e.printStackTrace();
        ErrorDto errorDto =  new ErrorDto(HttpStatus.BAD_REQUEST.value(),e.getMessage(),"","");
        return errorDto; 
    }
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
    public ErrorDto authenticationExHandler(AuthenticationException e) {
        log.error("error : AuthenticationException");
        e.printStackTrace();
        ErrorDto errorDto =  new ErrorDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage(),"","");
        return errorDto; 
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public List<ErrorDto> bindExHandler(BindException e) {
		log.error("error : BindException");
		e.printStackTrace();
		List<ErrorDto> errors = e.getBindingResult().getFieldErrors()
				.stream()
				.map(error -> new ErrorDto(HttpStatus.BAD_REQUEST.value(),error.getDefaultMessage(),error.getField(),error.getCode()))
				.collect(Collectors.toList());
		
		return errors;
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NotAuthException.class)
	public ErrorDto notAuthException(NotAuthException e) {
		log.error("error : NotAuthException "+e.getMessage());
		e.printStackTrace();
		ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage(),"","");
		return errorDto;
		
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NotFindMemberException.class)
	public ErrorDto notFindMemberException(NotFindMemberException e) {
		log.error("error : NotFindMemberException");
		e.printStackTrace();
		ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage(),"","");
		return errorDto;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NoSuchElementException.class)
	public ErrorDto noSuchElementException(NoSuchElementException e) {
		log.error("error : NoSuchElementException");
		e.printStackTrace();
		ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),"","noSuch");
		return errorDto;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public List<ErrorDto> handleBindException(ConstraintViolationException e, Locale locale) {
		log.error("error : handleBindException");
		e.printStackTrace();
		List<ErrorDto> errors = e.getConstraintViolations().stream()
				.map(error -> new ErrorDto(HttpStatus.BAD_REQUEST.value(),error.getMessage(),"binding",""))
				.collect(Collectors.toList());
		
		return errors;
	}
	
}
