package com.kelley.autoregistry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.mapper.OwnerMapper;
import com.kelley.autoregistry.model.Owner;
import com.kelley.autoregistry.repository.OwnerRepository;

@Service
public class OwnerServiceImpl implements OwnerService {
	
	private final OwnerMapper ownerMapper;
	
	private final OwnerRepository ownerRepository;
	
	public OwnerServiceImpl(OwnerMapper ownerMapper, OwnerRepository ownerRepository) {
		this.ownerMapper = ownerMapper;
		this.ownerRepository = ownerRepository;
	}
	
	@Override
	public OwnerDTO createOwner(OwnerDTO ownerDTO) {
		
		Owner owner = ownerMapper.toEntity(ownerDTO);
		
		owner = ownerRepository.save(owner);
		
		return ownerMapper.toDTO(owner);
	}

	@Override
	public OwnerDTO updateOwner(Long ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException {
		
		Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
		
		if (ownerOptional.isEmpty()) throw new OwnerNotFoundException("Owner not found with ID: " + ownerId);
		
		Owner owner = ownerOptional.get();
		
		if (ownerDTO.getFirstName() != null) {
			owner.setFirstName(ownerDTO.getFirstName());
		}
		
		if (ownerDTO.getLastName() != null) {
			owner.setLastName(ownerDTO.getLastName());
		}
		
		if (ownerDTO.getEmail() != null) {
			owner.setEmail(ownerDTO.getEmail());
		}
		
		if (ownerDTO.getPhoneNumber() != null) {
			owner.setPhoneNumber(ownerDTO.getPhoneNumber());
		}
		
		if (ownerDTO.getAddress() != null) {
			owner.setAddress(ownerDTO.getAddress());
		}
		
		owner = ownerRepository.save(owner);
		
		return ownerMapper.toDTO(owner);
		
		
	}

	@Override
	public OwnerDTO readOwner(Long ownerId) throws OwnerNotFoundException {
		
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		
		if (optionalOwner.isEmpty()) throw new OwnerNotFoundException("Owner not found with ID: " + ownerId);
		
		Owner owner = optionalOwner.get();
		
		return ownerMapper.toDTO(owner);
	}

	@Override
	public List<OwnerDTO> readAllOwners() {
		
		Iterable<Owner> owners = ownerRepository.findAll();
		
		List<OwnerDTO> ownersDTOs = new ArrayList<>();
		
		owners.forEach(owner -> ownersDTOs.add(ownerMapper.toDTO(owner)));
		
		return ownersDTOs;
	}

	@Override
	public List<OwnerDTO> findOwnersByEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOwner(Long ownerId) {
		// TODO Auto-generated method stub

	}

}
