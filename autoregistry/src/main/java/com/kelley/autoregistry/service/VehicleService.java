package com.kelley.autoregistry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kelley.autoregistry.dto.VehicleDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.exception.VehicleNotFoundException;

/**
 * Contract for implementing business logic relating to vehicle operations.
 */
public interface VehicleService {
	
	/**
	 * Add a vehicle to the database.
	 * 
	 * @param vehicleDTO - containing details needed to add vehicle to database
	 * @return vehicleDTO - return vehicle details after successful persistence
	 * OR OwnerNotFoundException if an OwnerId is provided by the client and no owner
	 * can be found.
	 */
	VehicleDTO addVehicle(VehicleDTO vehicleDTO) throws OwnerNotFoundException;
	
	/**
	 * Update an existing vehicle in the database.
	 * 
	 * @param vin - Vin number of the vehicle to be updated.
	 * @param vehicleDTO - containing vehicle details to be updated.
	 * @return VehicleDTO - return vehicle details after successful update or Exception if no vehicle found.
	 */
	VehicleDTO updateVehicle(String vin, VehicleDTO vehicleDTO) throws VehicleNotFoundException, OwnerNotFoundException;
	
	/**
	 * Search for an existing vehicle using the VIN number.
	 * 
	 * @param vin - Vin of the desired vehicle.
	 * @return VehicleDTO - return vehicle details if found or Exception if no vehicle or owner are found.
	 */
	VehicleDTO findVehicleByVin(String vin) throws VehicleNotFoundException;
	
	/**
	 * Return all vehicles linked to a particular owner, paginated
	 * 
	 * @param ownerId
	 * @param pageable
	 * @return Page with VehicleDTO objects
	 */
	Page<VehicleDTO> findVehiclesByOwnerId(Long ownerId, Pageable pageable);
	
	
	/**
	 * Delete a vehicle by its VIN number.
	 * 
	 * @param vin - Vin number of the vehicle to be removed.
	 * @return void or Exception if no vehicle found with matching VIN.
	 */
	void deleteVehicle(String vin) throws VehicleNotFoundException;
}
