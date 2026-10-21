package com.staylanka.staylanka.entity.room;


import jakarta.persistence.Entity;

@Entity

public class StandedRoom extends Room{

    public StandedRoom(String id, double price, int capacity, String status) {
        super(id, price, capacity, status);
    }
    public StandedRoom(){}

    public double calculatePrice(int nights){
        return getPricePerNight() * nights;
    }
    public String getType(){
        return "Standard";
    }
}
