package com.kelley.autoregistry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.dto.OwnerDTO;
import com.kelley.autoregistry.service.OwnerService;

@RestController
@RequestMapping(("/owner"))
public class OwnerController {
	
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<OwnerDTO> addOwner(@RequestBody OwnerDTO ownerDTO) {
		OwnerDTO savedOwnerDTO = ownerService.createOwner(ownerDTO);
		return ResponseEntity.status(201).body(savedOwnerDTO);
	}
	
}
