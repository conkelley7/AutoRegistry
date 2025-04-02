package com.kelley.autoregistry.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kelley.autoregistry.dto.VehicleDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.exception.VehicleNotFoundException;
import com.kelley.autoregistry.mapper.VehicleMapper;
import com.kelley.autoregistry.model.Owner;
import com.kelley.autoregistry.model.Vehicle;
import com.kelley.autoregistry.repository.OwnerRepository;
import com.kelley.autoregistry.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

	private final VehicleRepository vehicleRepository;
	private final OwnerRepository ownerRepository;
	private final VehicleMapper vehicleMapper;
	
	public VehicleServiceImpl(VehicleRepository vehicleRepository, OwnerRepository ownerRepository, VehicleMapper vehicleMapper) {
		this.vehicleRepository = vehicleRepository;
		this.ownerRepository = ownerRepository;
		this.vehicleMapper = vehicleMapper;
	}
	
	@Override
	public VehicleDTO addVehicle(VehicleDTO vehicleDTO) throws OwnerNotFoundException {
		
		Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
		
		vehicle = vehicleRepository.save(vehicle);
		
		return(vehicleMapper.toDTO(vehicle));
	}

	@Override
	public VehicleDTO updateVehicle(String vin, VehicleDTO vehicleDTO) throws VehicleNotFoundException, OwnerNotFoundException {
		
		Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
		
		if (optionalVehicle.isEmpty()) throw new VehicleNotFoundException("Vehicle not found with VIN: " + vin);
		
		Vehicle vehicle = optionalVehicle.get();
		
		if (vehicleDTO.getVin() != null) {
			vehicle.setVin(vehicleDTO.getVin());
		}
		
		if (vehicleDTO.getMake() != null) {
			vehicle.setMake(vehicleDTO.getMake());
		}
		
		if (vehicleDTO.getModel() != null) {
			vehicle.setModel(vehicleDTO.getModel());
		}
		
		if (vehicleDTO.getYear() != null) {
			vehicle.setYear(vehicleDTO.getYear());
		}
		
		if (vehicleDTO.getColor() != null) {
			vehicle.setColor(vehicleDTO.getColor());
		}
		
		if (vehicleDTO.getLicensePlate() != null) {
			vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
		}
		
		if (vehicleDTO.getOwnerId() != null) {
			// Check owners table for new Owner based on ID provided by client
			Optional<Owner> optionalOwner = ownerRepository.findById(vehicleDTO.getOwnerId());
			
			// If no Owner with provided ID is found throw Exception
			if (optionalOwner.isEmpty()) throw new OwnerNotFoundException("Owner not found with ID: " + vehicleDTO.getOwnerId());
			
			Owner owner = optionalOwner.get();
			
			vehicle.setOwner(owner);
		}
		
		if (vehicleDTO.getRegistrationDate() != null) {
			vehicle.setRegistrationDate(vehicleDTO.getRegistrationDate());
		}
		
		vehicle = vehicleRepository.save(vehicle);
		
		return vehicleMapper.toDTO(vehicle);
	}

	@Override
	public VehicleDTO findVehicleByVin(String vin) throws VehicleNotFoundException {
		
		Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
		
		if (optionalVehicle.isEmpty()) throw new VehicleNotFoundException("Vehicle not found with VIN: " + vin);
		
		Vehicle vehicle = optionalVehicle.get();
		
		return vehicleMapper.toDTO(vehicle);
	}
	
	@Override
	public Page<VehicleDTO> findVehiclesByOwnerId(Long ownerId, Pageable pageable) throws OwnerNotFoundException {
		
		Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
		
		if (ownerOptional.isEmpty()) throw new OwnerNotFoundException("Owner not found with ID: " + ownerId);
		
		Owner owner = ownerOptional.get();
		
		Page<Vehicle> vehicles = vehicleRepository.findByOwner(owner, pageable);
		
		return vehicles.map(vehicle -> vehicleMapper.toDTO(vehicle));
	}

	@Override
	public void deleteVehicle(String vin) throws VehicleNotFoundException {
		
		Optional<Vehicle> optionalVehicle = vehicleRepository.findByVin(vin);
		
		if (optionalVehicle.isEmpty()) throw new VehicleNotFoundException("Vehicle not found with VIN: " + vin);
		
		Vehicle vehicle = optionalVehicle.get();
		
		vehicleRepository.delete(vehicle);
	}

	

}
