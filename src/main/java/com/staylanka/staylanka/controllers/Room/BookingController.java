package com.staylanka.staylanka.controllers.Room;

import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.service.Room.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // create rest API
@CrossOrigin
@RequestMapping(value = "api/v1") // Rest API
public class BookingController {

    private BookingService bookingService;

    // Spring gives us the BookingService automatically
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // 1. READ ALL (and search by guest name)
    // GET /api/v1/bookings
    // GET /api/v1/bookings?keyword=Nimal
    @GetMapping("/bookings")
    public List<Booking> getAllBookings(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return bookingService.getAllBookings();
        }
        return bookingService.searchBookings(keyword);
    }

    // 2. READ ONE
    // GET /api/v1/bookings/1
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();          // 404 = not found
        }
        return ResponseEntity.ok(booking);                     // 200 = ok
    }

    // 3. CREATE
    // POST /api/v1/bookings   (booking details come in the request body as JSON)
    @PostMapping("/bookings")
    public ResponseEntity<String> addBooking(@RequestBody Booking booking) {
        try {
            Booking saved = bookingService.addBooking(booking);
            return ResponseEntity.ok("Booking confirmed. ID: " + saved.getBookingId()
                    + ", total price: " + saved.getTotalPrice());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());   // 400 = bad data
        }
    }

    // 4. UPDATE (guest details and dates)
    // PUT /api/v1/bookings/1
    @PutMapping("/bookings/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            Booking updated = bookingService.updateBooking(id, booking);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Booking updated. New total price: " + updated.getTotalPrice());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. CANCEL (the booking stays in the list as "Cancelled")
    // PUT /api/v1/bookings/1/cancel
    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        boolean cancelled = bookingService.cancelBooking(id);
        if (!cancelled) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Booking cancelled");
    }

    // 6. DELETE (removes the booking completely)
    // DELETE /api/v1/bookings/1
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Booking deleted");
    }
}