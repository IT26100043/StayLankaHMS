package com.staylanka.staylanka.entity.room;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public abstract class Room {
    @Id
    private String roomId;
    private double pricePerNight;
    private int capacity;
    // Maintenance period (both dates included). Both null = no maintenance planned.
    private LocalDate maintenanceFrom;
    private LocalDate maintenanceTo;

    public Room() {
        this.roomId = "Null";
        this.pricePerNight = 0;
        this.capacity = 0;
        this.maintenanceFrom = null;
        this.maintenanceTo = null;
    }

    public Room(String roomId, double pricePerNight, int capacity) {
        this(roomId, pricePerNight, capacity, null, null);
    }

    public Room(String roomId, double pricePerNight, int capacity, LocalDate maintenanceFrom, LocalDate maintenanceTo) {
        this.roomId = roomId;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.maintenanceFrom = maintenanceFrom;
        this.maintenanceTo = maintenanceTo;
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

    public LocalDate getMaintenanceFrom() {
        return maintenanceFrom;
    }

    public void setMaintenanceFrom(LocalDate maintenanceFrom) {
        this.maintenanceFrom = maintenanceFrom;
    }

    public LocalDate getMaintenanceTo() {
        return maintenanceTo;
    }

    public void setMaintenanceTo(LocalDate maintenanceTo) {
        this.maintenanceTo = maintenanceTo;
    }

    // true if a stay (check-in day up to the night before check-out) touches the maintenance period
    public boolean isUnderMaintenance(LocalDate checkIn, LocalDate checkOut) {
        if (maintenanceFrom == null || maintenanceTo == null) {
            return false;
        }
        return !checkIn.isAfter(maintenanceTo) && checkOut.isAfter(maintenanceFrom);
    }

    public abstract double calculatePrice(int nights);

    public abstract String getType();

    public String displayRoomDetails() {
        return getType() + " room " + roomId + " | Rs." + pricePerNight + "/night | sleeps " + capacity
                + (maintenanceFrom != null ? " | maintenance " + maintenanceFrom + " to " + maintenanceTo : "");
    }
}
