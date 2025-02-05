package com.kelley.autoregistry.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kelley.autoregistry.dto.VehicleDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.model.Owner;
import com.kelley.autoregistry.model.Vehicle;
import com.kelley.autoregistry.repository.OwnerRepository;


/**
 * Mapper class to convert between Vehicle entities and VehicleDTO objects.
 */
@Component
public class VehicleMapper {
	
	@Autowired
	OwnerRepository ownerRepository;
	
	public VehicleDTO toDTO(Vehicle vehicle) {
		if (vehicle == null) {
			return null;
		}
		
		Owner owner = vehicle.getOwner();
		
		if (owner == null) {
			return new VehicleDTO(
                    vehicle.getVin(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getYear(),
                    vehicle.getColor(),
                    vehicle.getLicensePlate(),
                    null,
                    vehicle.getRegistrationDate()
            );
		}
		
		
		return new VehicleDTO(
				vehicle.getVin(),
				vehicle.getMake(),
				vehicle.getModel(),
				vehicle.getYear(),
				vehicle.getColor(),
				vehicle.getLicensePlate(),
				vehicle.getOwner().getOwnerId(),
				vehicle.getRegistrationDate()
		);
	}
	
	public Vehicle toEntity(VehicleDTO vehicleDTO) throws OwnerNotFoundException {
		if (vehicleDTO == null) {
			return null;
		}
		
		Owner owner = null;
		
		// If the OwnerID provided in the DTO is NOT null, then get the Owner from the Repository.
		if (vehicleDTO.getOwnerId() != null) {
			Long ownerId = vehicleDTO.getOwnerId();
			Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
			
			if (optionalOwner.isEmpty()) throw new OwnerNotFoundException("Owner not found with ID: " + ownerId);
			
			owner = optionalOwner.get();
		}
		
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVin(vehicleDTO.getVin());
		vehicle.setMake(vehicleDTO.getMake());
		vehicle.setModel(vehicleDTO.getModel());
		vehicle.setYear(vehicleDTO.getYear());
		vehicle.setColor(vehicleDTO.getColor());
		vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
		vehicle.setOwner(owner);
		vehicle.setRegistrationDate(vehicleDTO.getRegistrationDate());
		
		return vehicle;
	}
}
