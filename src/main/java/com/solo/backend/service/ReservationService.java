package com.solo.backend.service;

import com.solo.backend.model.Reservation;
import com.solo.backend.model.User;
import com.solo.backend.model.Cabin;
import com.solo.backend.repository.ReservationRepository;
import com.solo.backend.repository.UserRepository;
import com.solo.backend.repository.CabinRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CabinRepository cabinRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, 
                              UserRepository userRepository, 
                              CabinRepository cabinRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.cabinRepository = cabinRepository;
    }

    // Create a new reservation
    public Reservation createReservation(Reservation reservation) {
        // Find the user and cabin by their respective IDs
        User user = userRepository.findById(reservation.getUser().getId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Cabin cabin = cabinRepository.findById(reservation.getCabin().getId())
                                     .orElseThrow(() -> new RuntimeException("Cabin not found"));

        // Set the user and cabin on the reservation (if not already set)
        reservation.setUser(user);
        reservation.setCabin(cabin);

        // Save the reservation to the database
        return reservationRepository.save(reservation);
    }

    // Get all reservations by user ID
    public List<Reservation> getReservationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUserId(user.getId());
    }

    // Get a reservation by ID
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    // List all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Update an existing reservation
    public Reservation updateReservation(Long id, Reservation reservation) {
        Optional<Reservation> existingReservationOpt = reservationRepository.findById(id);
        if (existingReservationOpt.isEmpty()) {
            return null;
        }

        User user = userRepository.findById(reservation.getUser().getId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Cabin cabin = cabinRepository.findById(reservation.getCabin().getId())
                                     .orElseThrow(() -> new RuntimeException("Cabin not found"));

        Reservation existingReservation = existingReservationOpt.get();
        existingReservation.setUser(user);
        existingReservation.setCabin(cabin);
        existingReservation.setCheckInDate(reservation.getCheckInDate());
        existingReservation.setCheckOutDate(reservation.getCheckOutDate());

        return reservationRepository.save(existingReservation);
    }

    // Delete a reservation by ID
    public boolean deleteReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return true; // Successfully deleted
        }
        return false; // Reservation not found
    }
}
