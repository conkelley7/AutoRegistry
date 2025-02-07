package com.kelley.autoregistry.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;

/**
 * Service class to generate and verify a signed JWT.
 */
@Service
public class JwtService {
	
	// 1 day in ms. Should be shorter in production.
	static final long EXPIRATION_TIME = 86400000;
	
	static final String PREFIX = "Bearer";
	
	/*
	 * For development I am just generating a random secret key.
	 * In production, it should be set in and read from application.properties
	 */
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	/**
	 * Generates and returns a signed JWT token
	 * 
	 * @param username
	 * @return signed JWT token
	 */
	public String getToken(String username) {
		String token = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key)
				.compact();
				
		return token;
	}
	
	/**
	 * Get token from authorization header, verify, and get username
	 * 
	 * @param request - HTTP request
	 * @return username
	 */
	public String getAuthUser(HttpServletRequest request) {
		// Get token from the request authorization header
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (token != null) {
			String username = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					// Verify and remove "Bearer" prefix
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody()
					// Gets username
					.getSubject();
			
			if (username != null) {
				return username;
			}
		}
		
		return null;
	}
	
	
	
}
