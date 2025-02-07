package com.kelley.autoregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kelley.autoregistry.model.AppUser;
import com.kelley.autoregistry.repository.AppUserRepository;

/**
 * Implements CommandLineRunner.
 * This is just for testing, not a secure implementation for production.
 * When Application begins, it will check if an admin exists in the database.
 * If not, an Admin will be created.
 * This admin can then be used to access other endpoints in the program after calling
 * the login endpoint with the credentials below and using the generated JWT.
 */
@Component
public class DataInitializer implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public DataInitializer(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
    public void run(String... args) throws Exception {
        if (!appUserRepository.existsByUsername("admin")) {
            String encodedPassword = passwordEncoder.encode("adminpassword");
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(encodedPassword);
            admin.setRole("ADMIN");
            appUserRepository.save(admin);
            logger.info("Admin user created successfully.");
        } else {
        	logger.info("Admin user already exists in database");
        }
    }
	
}
