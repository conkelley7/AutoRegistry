package com.kelley.autoregistry.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.dto.VehicleDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.service.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
	
	private final VehicleService vehicleService;
	
	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	
	/**
	 * 
	 * @param vehicleDTO - Containing vehicle details to add new vehicle to database.
	 * @return 200 OK Response Entity with vehicle details
	 * If an OwnerID is provided by the client, and the owner cannot be found in the database
	 * to match the ID, a 404 error is returned.
	 */
	@PostMapping("/add")
	public ResponseEntity<VehicleDTO> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
		vehicleService.addVehicle(vehicleDTO);
		return ResponseEntity.ok(vehicleDTO);
	}
	
	/*
	 * Learned about using an @ControllerAdvice class for global exception handling recently.
	 * If I had learned about it before implementing the OwnerController class fully, I would have used
	 * that instead. Definetly something to note for future projects.
	 */
	@ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<String> handleOwnerNotFoundException(OwnerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
	
	
	
	
}
