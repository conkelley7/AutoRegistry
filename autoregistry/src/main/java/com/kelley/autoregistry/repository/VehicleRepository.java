package com.kelley.autoregistry.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelley.autoregistry.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	Optional<Vehicle> findByVin(String vin);
}
