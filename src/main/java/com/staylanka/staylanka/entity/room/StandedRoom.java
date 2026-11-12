package com.staylanka.staylanka.entity.room;


import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity

public class StandedRoom extends Room{

    public StandedRoom(String id, double price, int capacity) {
        super(id, price, capacity);
    }
    public StandedRoom(String id, double price, int capacity, LocalDate maintenanceFrom, LocalDate maintenanceTo) {
        super(id, price, capacity, maintenanceFrom, maintenanceTo);
    }
    public StandedRoom(){}

    public double calculatePrice(int nights){
        return getPricePerNight() * nights;
    }
    public String getType(){
        return "Standard";
    }
}
