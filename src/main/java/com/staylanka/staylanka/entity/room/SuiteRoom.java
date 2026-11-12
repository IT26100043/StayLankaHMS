package com.staylanka.staylanka.entity.room;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity

public class SuiteRoom extends Room{

    private double serviceCharge = 1.2;

    public SuiteRoom(String id, double price, int capacity) {
        super(id, price, capacity);
    }
    public SuiteRoom(String id, double price, int capacity, LocalDate maintenanceFrom, LocalDate maintenanceTo) {
        super(id, price, capacity, maintenanceFrom, maintenanceTo);
    }
    public SuiteRoom(){}

    public double calculatePrice(int nights) {
        return getPricePerNight() * nights * serviceCharge ;
    }

    public String getType(){
        return "Suite";
    }
}
