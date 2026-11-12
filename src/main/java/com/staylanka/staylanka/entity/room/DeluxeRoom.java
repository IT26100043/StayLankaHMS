package com.staylanka.staylanka.entity.room;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity

public class DeluxeRoom extends Room {
    private double serviceCharge = 1.1;

    public DeluxeRoom(String id, double price, int capacity) {
        super(id, price, capacity);
    }
    public DeluxeRoom(String id, double price, int capacity, LocalDate maintenanceFrom, LocalDate maintenanceTo) {
        super(id, price, capacity, maintenanceFrom, maintenanceTo);
    }
    public DeluxeRoom(){}

    public double calculatePrice(int nights) {
        return getPricePerNight() * nights * serviceCharge;
    }

    public String getType(){
        return "Deluxe";
    }
}


