package com.kelley.autoregistry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kelley.autoregistry.exception.AuthEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final AuthenticationFilter authenticationFilter;
	private final AuthEntryPoint authEntryPoint;
	
	public SecurityConfig(UserDetailsService userDetailsService, AuthenticationFilter authenticationFilter, AuthEntryPoint authEntryPoint) {
		this.userDetailsService = userDetailsService;
		this.authenticationFilter = authenticationFilter;
		this.authEntryPoint = authEntryPoint;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	/*
	 * Defines that POST method requests to the /login endpoint do not require authentification,
	 * but all other requests to all other endpoints require authentification.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Define that Spring Security will never create a session and we can therefore disable CSRF
		http.csrf((csrf) -> csrf.disable())
		 .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(HttpMethod.POST, "api/v2/login").permitAll().anyRequest().authenticated())
		 .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
		 .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authEntryPoint));
		
		return http.build();
	}
}
