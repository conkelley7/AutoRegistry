package com.kelley.autoregistry;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kelley.autoregistry.model.AppUser;
import com.kelley.autoregistry.model.Owner;
import com.kelley.autoregistry.model.Vehicle;
import com.kelley.autoregistry.repository.AppUserRepository;
import com.kelley.autoregistry.repository.OwnerRepository;
import com.kelley.autoregistry.repository.VehicleRepository;

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
	private final OwnerRepository ownerRepository;
	private final VehicleRepository vehicleRepository;
	
	public DataInitializer(
			AppUserRepository appUserRepository, 
			BCryptPasswordEncoder passwordEncoder,
			OwnerRepository ownerRepository,
			VehicleRepository vehicleRepository
	) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.ownerRepository = ownerRepository;
		this.vehicleRepository = vehicleRepository;
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
        
        /*
         *  Sample owner and vehicle data
         *  
         *  Using create-drop for DB in application-docker.yml
         *  
         *  If you switch off create-drop and decide to persist in a volume,
         *  you can delete the data below
         */
        
        // TODO where I left off:
        // CREATE EXISTSBY in OWNER AND VEHICLE REPOS!!
        // THEN CHECK IF THE OWNER AND VEHICLE BELOW EXIST BEFORE CREATION!!!
        
        if (!ownerRepository.existsByEmail("john.doe@example.com")) {
        	Owner owner = new Owner();
        	owner.setFirstName("John");
        	owner.setLastName("Doe");
        	owner.setEmail("john.doe@example.com");
        	owner.setPhoneNumber("123-456-7890");
        	owner.setAddress("New Address");
		
        	owner = ownerRepository.save(owner);
		
        	Optional<Owner> ownerOptional = ownerRepository.findById(owner.getOwnerId());
        	if (ownerOptional.isEmpty()) {
        		logger.warn("Default owner creation failed");
        	} else {
        		logger.info("Default owner creation successful");
        	}
        } else {
        	logger.info("Default owner already exists");
        }
		
		
		if (!vehicleRepository.existsByVin("12345ABCDE")) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVin("12345ABCDE");
			vehicle.setMake("Ford");
			vehicle.setModel("F150");
			vehicle.setColor("White");
			vehicle.setLicensePlate("ABC123");
			vehicle.setYear(2015);
			vehicle.setRegistrationDate(LocalDate.now());
			if (ownerRepository.existsByEmail("john.doe@example.com")) {
				Pageable pageable = PageRequest.of(0, 1);
				Page<Owner> ownerPage = ownerRepository.findByEmail("john.doe@example.com", pageable);
				Owner owner = ownerPage.getContent().get(0);
				vehicle.setOwner(owner);
			}
			
			
			vehicle = vehicleRepository.save(vehicle);
			
			Optional<Vehicle> vehicleOptional = vehicleRepository.findByVin(vehicle.getVin());
			if (vehicleOptional.isEmpty()) {
				logger.warn("Default vehicle creation failed");
			} else {
				logger.info("Default vehicle creation successful");
			}
		} else {
			logger.info("Default vehicle already exists");
		}
		
    }
	
}
