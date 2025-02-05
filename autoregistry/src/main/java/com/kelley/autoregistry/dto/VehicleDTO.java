package com.kelley.autoregistry.dto;

import java.time.LocalDate;

public class VehicleDTO {

    private String vin;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private String licensePlate;
    private Long ownerId;
    private LocalDate registrationDate;

    public VehicleDTO() {
    }

    public VehicleDTO(String vin, String make, String model, int year, String color, 
                      String licensePlate, Long ownerId, LocalDate registrationDate) {
        
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.ownerId = ownerId;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters

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

    public Integer getYear() {
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}