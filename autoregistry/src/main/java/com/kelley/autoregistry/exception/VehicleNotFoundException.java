package com.kelley.autoregistry.exception;

@SuppressWarnings("serial")
public class VehicleNotFoundException extends RuntimeException {
	
	public VehicleNotFoundException(String message) {
		super(message);
	}
}
