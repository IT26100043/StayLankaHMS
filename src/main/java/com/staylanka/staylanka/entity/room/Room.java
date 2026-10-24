package com.staylanka.staylanka.entity.room;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class Room {
    @Id
    private String roomId;
    private double pricePerNight;
    private int capacity;
    private String status;

    public Room() {
        this.roomId = "Null";
        this.pricePerNight = 0;
        this.capacity = 0;
        this.status = "Null";
    }

    public Room(String roomId, double pricePerNight, int capacity, String status) {
        this.roomId = roomId;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.status = status;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract double calculatePrice(int nights);

    public abstract String getType();

    public String displayRoomDetails() {
        return getType() + " room " + roomId + " | Rs." + pricePerNight + "/night | sleeps " + capacity + " | " + status;
    }
}
