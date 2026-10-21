package com.staylanka.staylanka.entity.room;

import jakarta.persistence.Entity;

@Entity

public class SuiteRoom extends Room{

    private double serviceCharge = 1.2;

    public SuiteRoom(String id, double price, int capacity, String status) {
        super(id, price, capacity, status);
    }
    public SuiteRoom(){}

    public double calculatePrice(int nights) {
        return getPricePerNight() * nights * serviceCharge ;
    }

    public String getType(){
        return "Suite";
    }
}
