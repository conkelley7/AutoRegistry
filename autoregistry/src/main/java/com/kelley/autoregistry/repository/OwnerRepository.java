package com.kelley.autoregistry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kelley.autoregistry.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
	
	Optional<Owner> findById(long ownerId);
	
	Iterable<Owner> findByEmail(String email);
	
	/*
	 * Additional Queries created for extra Data JPA practice below.
	 * These aren't linked to any controller endpoints, just for fun.
	 * These (like set an Owner's name to John Doe) wouldn't really have
	 * a place in the application.
	 * Created tests for each in OwnerRepositoryTest.java
	 */
	
	// Delete Owners whose phone number starts with a certain prefix.
	@Modifying
	@Query("DELETE Owner o WHERE o.phoneNumber LIKE CONCAT (:phonePrefix, '%')")
	int deleteByPhoneNumberPrefix(@Param("phonePrefix") String phonePrefix);
	
	// Using Native SQL query to set a Owner's name to John Doe
	@Modifying
	@Query(value = "UPDATE owners SET first_name = 'John', last_name = 'Doe' WHERE owner_id = :ownerId", nativeQuery = true)
	int setOwnerNameToDefault(@Param("ownerId") Long ownerId);
}
