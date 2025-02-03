package com.kelley.autoregistry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kelley.autoregistry.dto.OwnerDTO;
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
		// Convert OwnerDTO to Owner Entity
		Owner owner = ownerMapper.toEntity(ownerDTO);
		// Persist Entity to Database
		owner = ownerRepository.save(owner);
		
		// Convert back to OwnerDTO and return
		return ownerMapper.toDTO(owner);
	}

	@Override
	public OwnerDTO updateOwner(OwnerDTO ownerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OwnerDTO> readOwner(Long ownerId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<OwnerDTO> readAllOwners() {
		// TODO Auto-generated method stub
		return null;
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
