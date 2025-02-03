package com.kelley.autoregistry.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kelley.autoregistry.model.Owner;

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
}
