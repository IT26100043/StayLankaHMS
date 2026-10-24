package com.staylanka.staylanka.repository.Room;

import com.staylanka.staylanka.entity.room.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // all bookings of one room with a given status (used to check the dates)
    List<Booking> findByRoomIdAndStatus(String roomId, String status);

    // search by guest name
    List<Booking> findByGuestNameContainingIgnoreCase(String keyword);
}