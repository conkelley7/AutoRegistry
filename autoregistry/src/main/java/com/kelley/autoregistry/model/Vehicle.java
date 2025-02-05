package com.kelley.autoregistry.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class mapping to 'vehicles' table in database.
 */
@Entity
@Table(name="vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vehicleId;
	
	// Marked as Natural ID for clarity, will be used in equals() implementation
	@NaturalId
	@Column(name = "vin", length = 17, nullable = false, unique = true)
	private String vin;
	
	@Column(name = "make", length = 100, nullable = false)
	private String make;
	
	@Column(name = "model", length = 100, nullable = false)
	private String model;
	
	@Column(name = "vehicle_year", length = 4, nullable = false)
	private int year;
	
	@Column(name = "color", length = 50)
	private String color;
	
	@Column(name = "license_plate", length = 20)
	private String licensePlate;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@Column(name = "registration_date")
	private LocalDate registrationDate;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	// Constructors
	
	public Vehicle() {
	}
	
	// Getters and Setters
	
	public Long getVehicleId() {
		return vehicleId;
	}
	
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof Vehicle other)) return false;
		
		return Objects.equals(this.getVin(), other.getVin());
	}
	
}
