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
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.service.OwnerService;

/*
 * Learned about using an @ControllerAdvice class for global exception handling recently.
 * If I had learned about it before implementing the OwnerController class fully, I would have used
 * that instead. Definetly something to note for future projects.
 * 
 * Check out my VehicleController.java class to see what I have learned and better exception handling
 * practices.
 * 
 * I may go back in the future and refactor this class and VehicleController.java to instead use a single
 * @ControllerAdvice class.
 */



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
	 * @return ResponseEntity with 200 OK and updated OwnerDTO or ResponseEntity with status 'Not Found'
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
	
	/**
	 * Find details for a specific owner in the datbase by Owner ID.
	 * 
	 * @param ownerId - ID of the owner to be found and returned
	 * @return ResponseEntity with 200 OK and found OwnerDTO or ResponseEntity with status 'Not Found'
	 */
	@GetMapping("/find/id/{ownerId}")
	public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long ownerId) {
		try {
			OwnerDTO foundOwnerDTO = ownerService.readOwner(ownerId);
			return ResponseEntity.ok(foundOwnerDTO);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	/**
	 * Return details for all owners in the database.
	 * 
	 * @return ResponseEntity with 200 OK and list of OwnerDTO objects or ResponseEntity with status 'Not Found'
	 */
	@GetMapping("/find")
	public ResponseEntity<List<OwnerDTO>> getAllOwners() {
		List<OwnerDTO> owners = ownerService.readAllOwners();
		
		if (owners.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
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
		
		if (owners.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		return ResponseEntity.ok(owners);
	}
	
	/**
	 * Delete a specific owner by ID.
	 * 
	 * @param ownerId
	 * @return ResponseEntity<Void> - No Content if successful, Not Found if Owner with ownerId cannot be found
	 */
	@DeleteMapping("/delete/id/{ownerId}")
	public ResponseEntity<Void> deleteOwnerById(@PathVariable Long ownerId) {
		try {
			ownerService.deleteOwner(ownerId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
