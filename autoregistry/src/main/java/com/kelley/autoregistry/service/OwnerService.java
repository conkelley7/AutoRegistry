package com.kelley.autoregistry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;

/**
 * Contract for implementing business logic related to Owner operations
 */
public interface OwnerService {
	
	/**
	 * Add new owner to the database.
	 * Convert incoming DTO to Owner and persist to database.
	 * @param ownerDTO - DTO containing new Owner information.
	 * @return ownerDTO - Updated DTO with changes applied.
	 */
	OwnerDTO createOwner(OwnerDTO ownerDTO);
	
	/**
	 * Update details of existing owner in the database.
	 * Update Owner entity in database with details included in DTO.
	 * @param ownerDTO - The DTO containing the updated owner details.
	 * @return ownerDTO - The updated DTO with changes applied.
	 */
	OwnerDTO updateOwner(Long ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException;
	
	/**
	 * Read owner details from Database for a single owner.
	 * @param ownerId - ID of the owner to get details for.
	 * @return Optional<OwnerDTO> containing owner details, if found.
	 */
	OwnerDTO getOwnerById(Long ownerId) throws OwnerNotFoundException;
	
	/**
	 * Read all owners from the database.
	 * @param pageable - pageable object for paginated results
	 * @return List of OwnerDTO objects for each owner in database.
	 */
	Page<OwnerDTO> getAllOwners(Pageable pageable);
	
	/**
	 * Read an owner(s) from database by email address.
	 * Email is not a unique field, so it is possible for more than one owner to be returned.
	 * @param email - String containing email address for the search.
	 * @return List<OwnerDTO> containing owner details, if found.
	 */
	Page<OwnerDTO> getOwnersByEmail(String email, Pageable pageable);
	
	/**
	 * Deletes an owner from the database.
	 * @param ownerId - Id of owner to be deleted.
	 */
	void deleteOwner(Long ownerId) throws OwnerNotFoundException;
	
}
