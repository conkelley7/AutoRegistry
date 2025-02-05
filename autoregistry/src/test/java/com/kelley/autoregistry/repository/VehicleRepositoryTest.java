package com.kelley.autoregistry.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.kelley.autoregistry.model.Owner;
import com.kelley.autoregistry.model.Vehicle;

@ActiveProfiles("test")
@DataJpaTest
public class VehicleRepositoryTest {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Test
	public void testAddVehicleWithOwner() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Doe");
		owner.setEmail("john.doe@example.com");
		owner.setPhoneNumber("123-456-7890");
		owner.setAddress("New Address");
		
		owner = ownerRepository.save(owner);
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVin("12345ABCDE");
		vehicle.setMake("Ford");
		vehicle.setModel("F150");
		vehicle.setColor("White");
		vehicle.setLicensePlate("ABC123");
		vehicle.setYear(2015);
		vehicle.setRegistrationDate(LocalDate.now());
		vehicle.setOwner(owner);
		
		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		
		assertEquals(vehicle, savedVehicle);
		assertEquals(vehicle.getOwner(), savedVehicle.getOwner());
	}
	
	@Test
	public void testAddVehicleWithoutOwner() {
		Vehicle vehicle = new Vehicle();
		vehicle.setVin("12345ABCDE");
		vehicle.setMake("Ford");
		vehicle.setModel("F150");
		vehicle.setColor("White");
		vehicle.setLicensePlate("ABC123");
		vehicle.setYear(2015);
		vehicle.setRegistrationDate(LocalDate.now());
		
		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		
		assertEquals(savedVehicle, vehicle);
		assertEquals(savedVehicle.getOwner(), null);
	}
}
