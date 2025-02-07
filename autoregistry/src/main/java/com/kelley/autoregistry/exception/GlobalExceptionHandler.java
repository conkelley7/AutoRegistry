package com.kelley.autoregistry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<String> handleOwnerNotFoundException(OwnerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
	
	@ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<String> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> handleUserNameNotFoundException(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(e.getMessage());
	}
}
