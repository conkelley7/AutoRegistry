package com.kelley.autoregistry.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.model.AccountCredentials;
import com.kelley.autoregistry.service.JwtService;

@RestController
@RequestMapping("api/v3")
public class LoginController {
	
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	/**
	 * Upon successful login, generates a JWT and sends back in response
	 * authorization header.
	 * 
	 * @param accountCredentials
	 * @return 200 OK with generated JWT in the header
	 */
	@PostMapping("/login")
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials) {
		UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(
				accountCredentials.username(),
				accountCredentials.password()
		);
		Authentication auth = authenticationManager.authenticate(creds);
		
		String jwts = jwtService.getToken(auth.getName());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization")
				.build();
	}
	
}
