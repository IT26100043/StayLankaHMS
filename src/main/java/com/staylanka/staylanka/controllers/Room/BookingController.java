package com.staylanka.staylanka.controllers.Room;

import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.service.Room.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.service.CustomerService;

@RestController // create rest API
@CrossOrigin
@RequestMapping(value = "api/v1") // Rest API
public class BookingController {

    private BookingService bookingService;
    private CustomerService customerService;

    // Spring gives us the BookingService automatically
    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
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

    @GetMapping("/customers/{customerId}/bookings")
    public ResponseEntity<List<Booking>> getCustomerBookings(@PathVariable Long customerId) {
        CustomerUser customer = customerService.getCustomerById(customerId).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookingService.getBookingsForCustomer(customerId, customer.getPhoneNumber()));
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

    //update
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


    @PutMapping("/bookings/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        boolean cancelled = bookingService.cancelBooking(id);
        if (!cancelled) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Booking cancelled");
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Booking deleted");
    }
}