package com.kelley.autoregistry.exception;

@SuppressWarnings("serial")
public class OwnerNotFoundException extends RuntimeException {

	public OwnerNotFoundException(String message) {
		super(message);
	}
}
