package com.kelley.autoregistry.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kelley.autoregistry.model.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
	
	Optional<Vehicle> findByVin(String vin);
}
