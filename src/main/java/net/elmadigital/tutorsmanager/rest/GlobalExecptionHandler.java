package net.elmadigital.tutorsmanager.rest;

import java.util.Arrays;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.elmadigital.tutorsmanager.exception.TutorAlreadyExistedException;
import net.elmadigital.tutorsmanager.exception.TutorNotFoundException;
import net.elmadigital.tutorsmanager.exception.TutorResponseError;

@ControllerAdvice
public class GlobalExecptionHandler {
	
	@ExceptionHandler
	public final ResponseEntity<TutorResponseError> handleTutorNotFoundException(TutorNotFoundException exc) {		
		TutorResponseError error = new TutorResponseError(HttpStatus.NOT_FOUND, "Tutor not found, sorry!", System.currentTimeMillis());
		return new ResponseEntity<>(error, error.getStatusCode());
	}
	
	@ExceptionHandler
	public final ResponseEntity<TutorResponseError> handleTutorAlreadyExistedException(TutorAlreadyExistedException exc) {
		TutorResponseError error = new TutorResponseError(HttpStatus.CONFLICT, "Tutor already existed, sorry!", System.currentTimeMillis());
		return new ResponseEntity<>(error, error.getStatusCode());
	}
	
	@ExceptionHandler
	public final ResponseEntity<TutorResponseError> handleTutorArgumentNotValidException(MethodArgumentNotValidException exc) {
		
		StringBuilder strErrorBuilder = new StringBuilder("invalid params -> ");
		exc.getBindingResult().getFieldErrors().forEach(fieldError -> {
			strErrorBuilder.append(fieldError.getRejectedValue() + " - " + fieldError.getField());
         });
		
		TutorResponseError error = new TutorResponseError(HttpStatus.BAD_REQUEST, strErrorBuilder.toString(), System.currentTimeMillis());
		return new ResponseEntity<TutorResponseError>(error, error.getStatusCode());
	}

}
