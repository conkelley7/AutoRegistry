package com.kelley.autoregistry.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kelley.autoregistry.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
	
	Optional<Owner> findById(long ownerId);
	
	Iterable<Owner> findByEmail(String email);
}
