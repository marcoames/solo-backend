package com.solo.backend.controller;

import com.solo.backend.model.Reservation;
import com.solo.backend.model.User;
import com.solo.backend.model.Cabin;
import com.solo.backend.service.ReservationService;
import com.solo.backend.service.UserService;
import com.solo.backend.service.CabinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api") // Group all endpoints under a common /api base
public class Controller {

    private final ReservationService reservationService;
    private final UserService userService;
    private final CabinService cabinService;

    // Constructor injection
    public Controller(ReservationService reservationService, UserService userService, CabinService cabinService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.cabinService = cabinService;
    }

    // Reservation endpoints

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> allReservations = reservationService.getAllReservations();
        return ResponseEntity.ok(allReservations);
    }

    @GetMapping("/reservations/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean isDeleted = reservationService.deleteReservation(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // User endpoints
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        return ResponseEntity.status(201).body(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // Cabin endpoints
    @GetMapping("/cabins")
    public ResponseEntity<List<Cabin>> getAllCabins() {
        List<Cabin> allCabins = cabinService.getAllCabins();
        return ResponseEntity.ok(allCabins);
    }

    @GetMapping("/cabins/{id}")
    public ResponseEntity<Cabin> getCabinById(@PathVariable Long id) {
        Optional<Cabin> cabin = cabinService.getCabinById(id);
        return cabin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cabins")
    public ResponseEntity<Cabin> createCabin(@RequestBody Cabin cabin) {
        Cabin createdCabin = cabinService.createCabin(cabin);
        return ResponseEntity.ok(createdCabin);
    }

    @PutMapping("/cabins/{id}")
    public ResponseEntity<Cabin> updateCabin(@PathVariable Long id, @RequestBody Cabin cabin) {
        Cabin updatedCabin = cabinService.updateCabin(id, cabin);
        if (updatedCabin != null) {
            return ResponseEntity.ok(updatedCabin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cabins/{id}")
    public ResponseEntity<Void> deleteCabin(@PathVariable Long id) {
        if (cabinService.deleteCabin(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    
}
