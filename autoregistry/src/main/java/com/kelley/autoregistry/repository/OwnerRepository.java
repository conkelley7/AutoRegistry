package com.kelley.autoregistry.repository;

import org.springframework.data.repository.CrudRepository;

import com.kelley.autoregistry.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
