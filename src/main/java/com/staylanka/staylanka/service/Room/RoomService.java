package com.staylanka.staylanka.service.Room;

import com.staylanka.staylanka.entity.room.Room;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    private void validate(Room room) {
        if (room.getRoomId() == null || room.getRoomId().isBlank()) {
            throw new IllegalArgumentException("Room ID is required");
        }
        if (room.getPricePerNight() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (room.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        LocalDate from = room.getMaintenanceFrom();
        LocalDate to = room.getMaintenanceTo();
        if ((from == null) != (to == null)) {
            throw new IllegalArgumentException("Give both maintenance dates (from and to), or leave both empty");
        }
        if (from != null && to.isBefore(from)) {
            throw new IllegalArgumentException("Maintenance end date cannot be before the start date");
        }
    }

    // CREATE
    public void addRoom(Room room) {
        validate(room);
        if (roomRepository.existsById(room.getRoomId())) {
            throw new IllegalArgumentException("Room ID already exists");
        }
        roomRepository.save(room);
    }

    // READ
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(String id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> searchRooms(String keyword) {
        String k = keyword.toLowerCase();
        List<Room> result = new ArrayList<>();
        for (Room r : roomRepository.findAll()) {
            if (r.getRoomId().toLowerCase().contains(k)
                    || r.getType().toLowerCase().contains(k)) {
                result.add(r);
            }
        }
        return result;
    }

    // UPDATE (the room type can change, so the old row is removed and the new one saved)
    @Transactional
    public boolean updateRoom(String id, Room room) {
        if (!roomRepository.existsById(id)) {
            return false;
        }
        room.setRoomId(id);   // the ID in the address is the one that counts
        validate(room);
        roomRepository.deleteById(id);
        roomRepository.flush();
        roomRepository.save(room);
        return true;
    }

    // DELETE
    public boolean deleteRoom(String id) {
        if (!roomRepository.existsById(id)) {
            return false;
        }
        roomRepository.deleteById(id);
        return true;
    }
}
