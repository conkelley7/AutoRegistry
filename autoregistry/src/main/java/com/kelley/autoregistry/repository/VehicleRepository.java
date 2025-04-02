package com.kelley.autoregistry.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kelley.autoregistry.model.Owner;
import com.kelley.autoregistry.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	Optional<Vehicle> findByVin(String vin);
	
	boolean existsByVin(String vin);
	
	Page<Vehicle> findByOwner(Owner owner, Pageable pageable);
}
