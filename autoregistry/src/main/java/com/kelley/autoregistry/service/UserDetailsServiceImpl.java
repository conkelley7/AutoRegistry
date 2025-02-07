package com.kelley.autoregistry.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kelley.autoregistry.model.AppUser;
import com.kelley.autoregistry.repository.AppUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AppUserRepository appUserRepository;
	
	public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}
	
	/**
	 * Finds an AppUser from the database and converts to UserDetails object
	 * 
	 * @param username
	 * @return UserDetails object required for authentification
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<AppUser> optionalUser = appUserRepository.findByUsername(username);
		UserBuilder builder = null;
		
		if (optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found with username: " + username);
		
		AppUser user = optionalUser.get();
		builder = User.withUsername(user.getUsername());
		builder.password(user.getPassword());
		builder.roles(user.getRole());
		
		return builder.build();
		
	}

}
