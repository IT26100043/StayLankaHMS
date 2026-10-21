package com.staylanka.staylanka.controllers.Room;


import com.staylanka.staylanka.entity.room.DeluxeRoom;
import com.staylanka.staylanka.entity.room.Room;
import com.staylanka.staylanka.entity.room.StandedRoom;
import com.staylanka.staylanka.entity.room.SuiteRoom;
import com.staylanka.staylanka.service.Room.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // create rest API
@CrossOrigin
@RequestMapping(value = "api/v1") // Rest API
public class RoomController {

    private final RoomService roomService;


    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Builds the correct room class (Standard / Deluxe / Suite) from the JSON body
    private Room buildRoom(Map<String, Object> body) {
        try {
            String type = String.valueOf(body.get("type"));
            String roomId = body.get("roomId") == null ? null : String.valueOf(body.get("roomId"));
            double price = Double.parseDouble(String.valueOf(body.get("pricePerNight")));
            int capacity = Integer.parseInt(String.valueOf(body.get("capacity")));
            String status = String.valueOf(body.get("status"));

            return switch (type) {
                case "Standard" -> new StandedRoom(roomId, price, capacity, status);
                case "Deluxe"   -> new DeluxeRoom(roomId, price, capacity, status);
                case "Suite"    -> new SuiteRoom(roomId, price, capacity, status);
                default -> throw new IllegalArgumentException("Unknown room type: " + type);
            };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Price and capacity must be valid numbers");
        }
    }


    @GetMapping("/rooms")
    public List<Room> getAllRooms(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return roomService.getAllRooms();
        }
        return roomService.searchRooms(keyword);
    }


    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable String id) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();      // 404 = not found
        }
        return ResponseEntity.ok(room);                    // 200 = ok
    }


    @PostMapping("/rooms")
    public ResponseEntity<String> addRoom(@RequestBody Map<String, Object> body) {
        try {
            roomService.addRoom(buildRoom(body));
            return ResponseEntity.ok("Room added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());   // 400 = bad data
        }
    }


    @PutMapping("/rooms/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable String id, @RequestBody Map<String, Object> body) {
        try {
            boolean updated = roomService.updateRoom(id, buildRoom(body));
            if (!updated) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Room updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable String id) {
        boolean deleted = roomService.deleteRoom(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Room deleted");
    }
}
