package com.staylanka.staylanka.entity.room;

import jakarta.persistence.Entity;

@Entity

public class DeluxeRoom extends Room {
    private double serviceCharge = 1.1;

    public DeluxeRoom(String id, double price, int capacity, String status) {
        super(id, price, capacity, status);
    }
    public DeluxeRoom(){}

    public double calculatePrice(int nights) {
        return getPricePerNight() * nights * serviceCharge;
    }

    public String getType(){
        return "Deluxe";
    }
}


