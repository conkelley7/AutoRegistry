package com.kelley.autoregistry.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.service.OwnerService;


@RestController
@RequestMapping("api/v3/owner")
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
	@PostMapping("")
	public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDTO) {
		OwnerDTO savedOwnerDTO = ownerService.createOwner(ownerDTO);
		return ResponseEntity.status(201).body(savedOwnerDTO);
	}
	
	/**
	 * Update an existing owner in the database.
	 * @param ownerDTO - containing information to be updated
	 * @param ownerId - ID of the owner to be updated
	 * @return ResponseEntity with 200 OK and updated OwnerDTO or ResponseEntity with status 'Not Found'
	 */
	@PutMapping("/{ownerId}")
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
	@GetMapping("/{ownerId}")
	public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long ownerId) {
		OwnerDTO ownerDTO = ownerService.getOwnerById(ownerId);
		return ResponseEntity.ok(ownerDTO);
	}
	
	/**
	 * Return details for all owners in the database (paginated).
	 * 
	 * @param page - the page number (default: 1, first page)
	 * @param size - page size (default: 10)
	 * @return ResponseEntity with 200 OK and list of OwnerDTO objects or ResponseEntity with status 'Not Found'
	 */
	@GetMapping("")
	public ResponseEntity<Page<OwnerDTO>> getAllOwners(
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="10") int size
	) {
				
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<OwnerDTO> owners = ownerService.getAllOwners(pageable);
		
		if (owners.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.ok(owners);
		
	}
	
	/**
	 * Search for a specific owner by their email.
	 * 
	 * @param email - email address to be used for search
	 * @return ResponseEntity with 200 OK and list of OwnerDTO objects or ResponseEntity with status 'Not Found'
	 */
	@GetMapping("/email/{email}")
	public ResponseEntity<Page<OwnerDTO>> getOwnersByEmail(
			@PathVariable String email,
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="10") int size
	) {
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<OwnerDTO> owners = ownerService.getOwnersByEmail(email, pageable);
		
		if (owners.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		return ResponseEntity.ok(owners);
	}
	
	/**
	 * Delete a specific owner by ID.
	 * 
	 * @param ownerId - Id of the Owner to delete from the database.
	 * @return ResponseEntity - 204 NO CONTENT if deletion successful.
	 */
	@DeleteMapping("/{ownerId}")
	public ResponseEntity<Void> deleteOwnerById(@PathVariable Long ownerId) {
		ownerService.deleteOwner(ownerId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
