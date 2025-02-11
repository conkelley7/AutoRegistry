package com.kelley.autoregistry.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.service.OwnerService;


@RestController
@RequestMapping(("api/v2/owner"))
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
	 * @return ResponseEntity with 200 OK and updated OwnerDTO or ResponseEntity with status 'Not Found'
	 */
	@PutMapping("/update/{ownerId}")
	public ResponseEntity<OwnerDTO> updateOwner(@RequestBody OwnerDTO ownerDTO, @PathVariable Long ownerId) {
		ownerDTO = ownerService.updateOwner(ownerId, ownerDTO);
		return ResponseEntity.ok(ownerDTO);
	}
	
	/**
	 * Find details for a specific owner in the datbase by Owner ID.
	 * 
	 * @param ownerId - ID of the owner to be found and returned
	 * @return ResponseEntity with 200 OK and found OwnerDTO
	 */
	@GetMapping("/find/id/{ownerId}")
	public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long ownerId) {
		OwnerDTO ownerDTO = ownerService.readOwner(ownerId);
		return ResponseEntity.ok(ownerDTO);
	}
	
	/**
	 * Return details for all owners in the database.
	 * 
	 * @return ResponseEntity with 200 OK and list of OwnerDTO objects or ResponseEntity with status 'Not Found'
	 */
	@GetMapping("/find")
	public ResponseEntity<List<OwnerDTO>> getAllOwners() {
		List<OwnerDTO> owners = ownerService.readAllOwners();
		
		if (owners.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.ok(owners);
		
	}
	
	/**
	 * Search for a specific owner by their email.
	 * 
	 * @param email - email address to be used for search
	 * @return ResponseEntity with 200 OK and list of OwnerDTO objects or ResponseEntity with status 'Not Found'
	 */
	@GetMapping("/find/email/{email}")
	public ResponseEntity<List<OwnerDTO>> getOwnersByEmail(@PathVariable String email) {
		List<OwnerDTO> owners = ownerService.findOwnersByEmail(email);
		
		if (owners.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.ok(owners);
	}
	
	/**
	 * Delete a specific owner by ID.
	 * 
	 * @param ownerId - Id of the Owner to delete from the database.
	 * @return ResponseEntity - 204 NO CONTENT if deletion successful.
	 */
	@DeleteMapping("/delete/id/{ownerId}")
	public ResponseEntity<Void> deleteOwnerById(@PathVariable Long ownerId) {
		ownerService.deleteOwner(ownerId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
