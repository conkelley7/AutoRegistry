package com.kelley.autoregistry.service;

import org.springframework.stereotype.Service;

import com.kelley.autoregistry.dto.VehicleDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.exception.VehicleNotFoundException;
import com.kelley.autoregistry.mapper.VehicleMapper;
import com.kelley.autoregistry.model.Vehicle;
import com.kelley.autoregistry.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

	private final VehicleRepository vehicleRepository;
	private final VehicleMapper vehicleMapper;
	
	public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
		this.vehicleRepository = vehicleRepository;
		this.vehicleMapper = vehicleMapper;
	}
	
	@Override
	public VehicleDTO addVehicle(VehicleDTO vehicleDTO) throws OwnerNotFoundException {
		
		Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
		
		vehicle = vehicleRepository.save(vehicle);
		
		return(vehicleMapper.toDTO(vehicle));
	}

	@Override
	public VehicleDTO updateVehicle(String vin, VehicleDTO vehicleDTO) throws VehicleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleDTO searchVehicle(String vin) throws VehicleNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVehicle(String vin) throws VehicleNotFoundException {
		// TODO Auto-generated method stub

	}

}
