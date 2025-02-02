package com.kelley.autoregistry.repository;

import org.springframework.data.repository.CrudRepository;

import com.kelley.autoregistry.model.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
}
