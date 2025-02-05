package com.kelley.autoregistry.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelley.autoregistry.dto.VehicleDTO;
import com.kelley.autoregistry.exception.OwnerNotFoundException;
import com.kelley.autoregistry.exception.VehicleNotFoundException;
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
		vehicleDTO = vehicleService.addVehicle(vehicleDTO);
		return ResponseEntity.ok(vehicleDTO);
	}
	
	/**
	 * Update an existing vehicle in the database.
	 * 
	 * @param vehicleDTO - containing update details
	 * @param vin - To identify the vehicle in the database to update
	 * @return ResponseEntity with 200 OK and updated vehicle details
	 */
	@PutMapping("/update/{vin}")
	public ResponseEntity<VehicleDTO> updateVehicle(@RequestBody VehicleDTO vehicleDTO, @PathVariable String vin) {
		vehicleDTO = vehicleService.updateVehicle(vin, vehicleDTO);
		return ResponseEntity.ok(vehicleDTO);
	}
	
	/**
	 * Search for a specific vehicle by the VIN number.
	 * 
	 * @param vin - Vin number of the vehicle for the search
	 * @return 200 OK with found vehicle details
	 */
	@GetMapping("/find/{vin}")
	public ResponseEntity<VehicleDTO> findVehicleByVin(@PathVariable String vin) {
		VehicleDTO vehicleDTO = vehicleService.searchVehicle(vin);
		return ResponseEntity.ok(vehicleDTO);
	}
	
	/**
	 * Delete a specific vehicle by the VIN number.
	 * 
	 * @param vin - VIN number of the vehicle to delete.
	 * @return ResponseEntity 204 No Content if successful.
	 */
	@DeleteMapping("/delete/{vin}")
	public ResponseEntity<Void> deleteVehicleByVin(@PathVariable String vin) {
		vehicleService.deleteVehicle(vin);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
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
	
	@ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<String> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
	
	
	
	
	
	
}
