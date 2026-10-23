package com.staylanka.staylanka.entity.room;

import com.staylanka.staylanka.entity.room.DeluxeRoom;
import com.staylanka.staylanka.entity.room.StandedRoom;
import com.staylanka.staylanka.entity.room.SuiteRoom;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DummyRooms implements CommandLineRunner {

    private RoomRepository roomRepository;

    public DummyRooms(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {

        // if there are already rooms in the database, do nothing
        if (roomRepository.count() > 0) {
            return;
        }

        // Standard rooms
        roomRepository.save(new StandedRoom("R101", 5000, 2, "Available"));
        roomRepository.save(new StandedRoom("R102", 5000, 2, "Booked"));
        roomRepository.save(new StandedRoom("R103", 5500, 3, "Available"));

        // Deluxe rooms
        roomRepository.save(new DeluxeRoom("R201", 8500, 2, "Available"));
        roomRepository.save(new DeluxeRoom("R202", 9000, 3, "Booked"));
        roomRepository.save(new DeluxeRoom("R203", 9500, 4, "Available"));

        // Suites
        roomRepository.save(new SuiteRoom("R301", 15000, 4, "Available"));
        roomRepository.save(new SuiteRoom("R302", 18000, 4, "Maintenance"));

    }
}