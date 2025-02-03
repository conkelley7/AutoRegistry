package com.kelley.autoregistry.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.service.OwnerService;

@RestController
@RequestMapping(("/owner"))
public class OwnerController {
	
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}
	
	/**
	 * Add a new owner to the database.
	 * 
	 * @param ownerDTO
	 * @return ResponseEntity - Status 201 for successful creation with Owner DTO
	 */
	@PostMapping("/add")
	public ResponseEntity<OwnerDTO> addOwner(@RequestBody OwnerDTO ownerDTO) {
		OwnerDTO savedOwnerDTO = ownerService.createOwner(ownerDTO);
		return ResponseEntity.status(201).body(savedOwnerDTO);
	}
	
	/**
	 * Update an existing owner in the database.
	 * @param ownerDTO - containing information to be updated
	 * @param ownerId - ID of the owner to be updated
	 * @return ResponseEntity with 200 OK and updated Owner DTO or ResponseEntity with status 'Not Found'
	 */
	@PutMapping("/update/{ownerId}")
	public ResponseEntity<OwnerDTO> updateOwner(@RequestBody OwnerDTO ownerDTO, @PathVariable Long ownerId) {
		try {
			OwnerDTO updatedOwnerDTO = ownerService.updateOwner(ownerId, ownerDTO);
			return ResponseEntity.ok(updatedOwnerDTO);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
}
