package com.kelley.autoregistry.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kelley.autoregistry.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	
	public AuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	/**
	 * Authentication Implementation.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// Get token from authorization header
		String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
				
		if (jws != null) {
			// Verify token and get user
			String user = jwtService.getAuthUser(request);
					
			// Authenticate
			Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,Collections.emptyList());
					
			// Where Spring stores details of the authenticated user
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

}
