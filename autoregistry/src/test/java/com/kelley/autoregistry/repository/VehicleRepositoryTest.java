package com.kelley.autoregistry.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	@Test
	public void testFindByVin() {
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVin("12345ABCDE");
		vehicle1.setMake("Ford");
		vehicle1.setModel("F150");
		vehicle1.setColor("White");
		vehicle1.setLicensePlate("ABC123");
		vehicle1.setYear(2015);
		vehicle1.setRegistrationDate(LocalDate.now());
		
		vehicleRepository.save(vehicle1);
		
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVin("019IDJABDF9278");
		vehicle2.setMake("Honda");
		vehicle2.setModel("Civic");
		vehicle2.setColor("Black");
		vehicle2.setLicensePlate("ACC123");
		vehicle2.setYear(2018);
		vehicle2.setRegistrationDate(LocalDate.now());
		
		vehicleRepository.save(vehicle2);
		
		Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin("12345ABCDE");
		Vehicle foundVehicle = optionalVehicle.get();
		assertEquals(vehicle1, foundVehicle);
	}
	
	@Test
	public void testFindByOwner() {
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
		
		vehicle = vehicleRepository.save(vehicle);
		
		Pageable pageable = PageRequest.of(0, 1);
		
		Page<Vehicle> sameVehiclePage = vehicleRepository.findByOwner(owner, pageable);
		Vehicle sameVehicle = sameVehiclePage.getContent().get(0);
		
		assertEquals(vehicle, sameVehicle);
	}
}
