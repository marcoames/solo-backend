package com.solo.backend.controller;

import com.solo.backend.dto.ReservationDTO;
import com.solo.backend.model.Reservation;
import com.solo.backend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // Constructor injection
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO dto) {
        // Map fields from DTO to the Reservation model and create the reservation
        Reservation createdReservation = reservationService.createReservation(dto);
        
        // If the reservation creation is successful, return status 201 (Created)
        return ResponseEntity.status(201).body(createdReservation);
    }

    // Get all reservations by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        
        // If no reservations found, return 404 (Not Found)
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(reservations);
    }

    // Get a reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        
        // Return 200 (OK) if reservation exists, or 404 (Not Found) if not
        return reservation.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing reservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO dto) {
        // Update the reservation based on the provided ID and DTO
        Reservation updatedReservation = reservationService.updateReservation(id, dto);
        
        // If reservation not found, return 404 (Not Found)
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(updatedReservation);
    }

    // Delete a reservation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        // Delete the reservation by ID
        boolean isDeleted = reservationService.deleteReservation(id);
        
        // If the reservation does not exist, return 404 (Not Found)
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }
}
