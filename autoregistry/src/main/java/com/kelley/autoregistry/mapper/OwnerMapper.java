package com.kelley.autoregistry.mapper;

import org.springframework.stereotype.Component;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.model.Owner;

/**
 * Mapper class to convert between Owner entity and OwnerDTO.
 */
@Component
public class OwnerMapper {

    /**
     * Converts an Owner entity to OwnerDTO.
     * 
     * @param owner - The Owner entity
     * @return The corresponding OwnerDTO
     */
    public OwnerDTO toDTO(Owner owner) {
        if (owner == null) {
            return null;
        }
        return new OwnerDTO(
            owner.getOwnerId(),
            owner.getFirstName(),
            owner.getLastName(),
            owner.getEmail(),
            owner.getPhoneNumber(),
            owner.getAddress()
        );
    }

    /**
     * Converts an OwnerDTO to an Owner entity.
     * 
     * @param ownerDTO - The OwnerDTO
     * @return The corresponding Owner entity
     */
    public Owner toEntity(OwnerDTO ownerDTO) {
        if (ownerDTO == null) {
            return null;
        }
        Owner owner = new Owner();
        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setEmail(ownerDTO.getEmail());
        owner.setPhoneNumber(ownerDTO.getPhoneNumber());
        owner.setAddress(ownerDTO.getAddress());
        return owner;
    }
}
