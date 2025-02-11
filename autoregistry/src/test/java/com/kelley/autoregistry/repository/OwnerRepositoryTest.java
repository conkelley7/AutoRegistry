package com.kelley.autoregistry.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.kelley.autoregistry.model.Owner;

@ActiveProfiles("test")
@DataJpaTest
public class OwnerRepositoryTest {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Test
	public void testSaveOwner() {
		Owner owner = new Owner();
		owner.setFirstName("John");
		owner.setLastName("Doe");
		owner.setEmail("john.doe@example.com");
		owner.setPhoneNumber("123-456-7890");
		owner.setAddress("New Address");
		
		Owner savedOwner = ownerRepository.save(owner);
		
		assertNotNull(savedOwner.getOwnerId());
		assertNotNull(savedOwner.getCreatedAt());
		assertNotNull(savedOwner.getUpdatedAt());
		
		assertEquals(owner.getFirstName(), savedOwner.getFirstName());
		assertEquals(owner.getLastName(), savedOwner.getLastName());
	}
	
	@Test
	public void testFindByEmail() {
		Owner owner1 = new Owner();
		owner1.setFirstName("John");
		owner1.setLastName("Doe");
		owner1.setEmail("john.doe@example.com");
		owner1.setPhoneNumber("123-456-7890");
		owner1.setAddress("New Address");
		
		ownerRepository.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Jane");
		owner2.setLastName("Doe");
		owner2.setEmail("jane.doe@example.com");
		owner2.setPhoneNumber("123-456-7890");
		owner2.setAddress("New Address");
		
		ownerRepository.save(owner2);
		
		Iterable<Owner> foundOwners = ownerRepository.findByEmail("john.doe@example.com");
		
		int size = 0;
		Owner foundOwner = null;
		for (Owner owner: foundOwners) {
			foundOwner = owner;
			size++;
		}
		assertEquals(size, 1);
		assertEquals(foundOwner, owner1);
	}
	
	@Test
	public void testFindAll() {
		Owner owner1 = new Owner();
		owner1.setFirstName("John");
		owner1.setLastName("Doe");
		owner1.setEmail("john.doe@example.com");
		owner1.setPhoneNumber("123-456-7890");
		owner1.setAddress("New Address");
		
		ownerRepository.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Jane");
		owner2.setLastName("Doe");
		owner2.setEmail("jane.doe@example.com");
		owner2.setPhoneNumber("123-456-7890");
		owner2.setAddress("New Address");
		
		ownerRepository.save(owner2);
		
		Iterable<Owner> foundOwners = ownerRepository.findAll();
		
		int size = 0;
		for (@SuppressWarnings("unused") Owner owner: foundOwners) {
			size++;
		}
		assertEquals(size, 2);
	}
	
	@Test
	public void testDeleteOwner() {
		Owner owner1 = new Owner();
		owner1.setFirstName("John");
		owner1.setLastName("Doe");
		owner1.setEmail("john.doe@example.com");
		owner1.setPhoneNumber("123-456-7890");
		owner1.setAddress("New Address");
		
		owner1 = ownerRepository.save(owner1);
		
		ownerRepository.delete(owner1);
		
		Iterable<Owner> foundOwners = ownerRepository.findAll();
		
		int size = 0;
		for (@SuppressWarnings("unused") Owner owner: foundOwners) {
			size++;
		}
		
		assertEquals(size, 0);
	}
	
	@Test
	public void testDeleteExpiredOwners() {
		Owner owner1 = new Owner();
		owner1.setFirstName("John");
		owner1.setLastName("Doe");
		owner1.setEmail("john.doe@example.com");
		owner1.setPhoneNumber("123-456-7890");
		owner1.setAddress("New Address");
		
		owner1 = ownerRepository.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Jane");
		owner2.setLastName("Doe");
		owner2.setEmail("jane.doe@example.com");
		owner2.setPhoneNumber("152-456-7890");
		owner2.setAddress("New Address");
		
		owner2 = ownerRepository.save(owner2);
		
		String phonePrefix = "152-";
		
		int deletedOwners = ownerRepository.deleteByPhoneNumberPrefix(phonePrefix);
		
		assertEquals(1, deletedOwners);
		
		Optional<Owner> remainingOwner = ownerRepository.findById(owner1.getOwnerId());
		assertTrue(remainingOwner.isPresent());
	}
	
	@Test 
	public void testSetOwnerNameToDefault() {
		Owner owner1 = new Owner();
		owner1.setFirstName("John");
		owner1.setLastName("Doe");
		owner1.setEmail("john.doe@example.com");
		owner1.setPhoneNumber("123-456-7890");
		owner1.setAddress("New Address");
		
		owner1 = ownerRepository.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Jane");
		owner2.setLastName("Doe");
		owner2.setEmail("jane.doe@example.com");
		owner2.setPhoneNumber("152-456-7890");
		owner2.setAddress("New Address");
		
		owner2 = ownerRepository.save(owner2);
		
		Long ownerId = owner1.getOwnerId();
		int updatedOwners = ownerRepository.setOwnerNameToDefault(ownerId);
		
		Optional<Owner> optionalUpdatedOwner = ownerRepository.findById(ownerId);
		
		Owner updatedOwner = optionalUpdatedOwner.get();
		
		assertEquals(1, updatedOwners);
		assertEquals("John", updatedOwner.getFirstName());
		assertEquals("Doe", updatedOwner.getLastName());
	}
	
}
