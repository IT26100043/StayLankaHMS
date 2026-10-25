package com.staylanka.staylanka.service.Room;

import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.entity.room.Room;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public Booking addBooking(Booking booking) {
        checkDetails(booking);

        if (booking.getCheckIn().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        Room room = roomRepository.findById(booking.getRoomId()).orElse(null);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        checkMaintenance(room, booking);

        checkRoomIsFree(booking, null);

        booking.setBookingId(null);                          // always create a new booking
        booking.setTotalPrice(calculateTotal(room, booking));
        booking.setStatus("Confirmed");
        return bookingRepository.save(booking);
    }

    // see booked lisst
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> searchBookings(String keyword) {
        return bookingRepository.findByGuestNameContainingIgnoreCase(keyword);
    }

    // updte
    public Booking updateBooking(Long id, Booking updated) {
        Booking existing = bookingRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        if ("Cancelled".equals(existing.getStatus())) {
            throw new IllegalArgumentException("A cancelled booking cannot be changed");
        }

        existing.setGuestName(updated.getGuestName());
        existing.setGuestPhone(updated.getGuestPhone());
        existing.setCheckIn(updated.getCheckIn());
        existing.setCheckOut(updated.getCheckOut());

        checkDetails(existing);
        checkRoomIsFree(existing, id);        // ignore this booking itself

        Room room = roomRepository.findById(existing.getRoomId()).orElse(null);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        checkMaintenance(room, existing);
        existing.setTotalPrice(calculateTotal(room, existing));
        return bookingRepository.save(existing);
    }

    // close booking
    public boolean cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            return false;
        }
        booking.setStatus("Cancelled");
        bookingRepository.save(booking);
        return true;
    }

    // delet
    public boolean deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            return false;
        }
        bookingRepository.deleteById(id);
        return true;
    }




    private void checkDetails(Booking booking) {
        if (booking.getRoomId() == null || booking.getRoomId().isBlank()) {
            throw new IllegalArgumentException("Room ID is required");
        }
        if (booking.getGuestName() == null || booking.getGuestName().isBlank()) {
            throw new IllegalArgumentException("Guest name is required");
        }
        if (booking.getCheckIn() == null || booking.getCheckOut() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required");
        }
        if (!booking.getCheckOut().isAfter(booking.getCheckIn())) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }
    }

    // the room cannot be booked on days that fall inside its maintenance period
    private void checkMaintenance(Room room, Booking booking) {
        if (room.isUnderMaintenance(booking.getCheckIn(), booking.getCheckOut())) {
            throw new IllegalArgumentException("Room " + room.getRoomId() + " is under maintenance from "
                    + room.getMaintenanceFrom() + " to " + room.getMaintenanceTo()
                    + ". Please choose other dates.");
        }
    }

    // if room alredy booked
    private void checkRoomIsFree(Booking newBooking, Long ignoreId) {
        List<Booking> confirmed = bookingRepository.findByRoomIdAndStatus(newBooking.getRoomId(), "Confirmed");
        for (Booking other : confirmed) {
            if (ignoreId != null && other.getBookingId().equals(ignoreId)) {
                continue;
            }
            boolean overlap = newBooking.getCheckIn().isBefore(other.getCheckOut())
                    && newBooking.getCheckOut().isAfter(other.getCheckIn());
            if (overlap) {
                throw new IllegalArgumentException("This room is already booked for those dates");
            }
        }
    }


    private double calculateTotal(Room room, Booking booking) {
        long nights = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        return room.calculatePrice((int) nights);
    }
}