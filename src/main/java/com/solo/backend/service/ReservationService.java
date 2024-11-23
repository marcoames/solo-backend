package com.solo.backend.service;

import com.solo.backend.model.Reservation;
import com.solo.backend.model.User;
import com.solo.backend.model.Hotel;
import com.solo.backend.repository.ReservationRepository;
import com.solo.backend.repository.UserRepository;
import com.solo.backend.repository.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, 
                              UserRepository userRepository, 
                              HotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    // Create a new reservation
    public Reservation createReservation(Reservation reservation) {
        // Find the user and hotel by their respective IDs
        User user = userRepository.findById(reservation.getUser().getId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Hotel hotel = hotelRepository.findById(reservation.getHotel().getId())
                                     .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Set the user and hotel on the reservation (if not already set)
        reservation.setUser(user);
        reservation.setHotel(hotel);

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

    // Update an existing reservation
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                                                              .orElseThrow(() -> new RuntimeException("Reservation not found"));
        User user = userRepository.findById(reservation.getUser().getId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Hotel hotel = hotelRepository.findById(reservation.getHotel().getId())
                                     .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Update reservation details
        existingReservation.setUser(user);
        existingReservation.setHotel(hotel);
        existingReservation.setCheckInDate(reservation.getCheckInDate());
        existingReservation.setCheckOutDate(reservation.getCheckOutDate());
        existingReservation.setStatus(reservation.getStatus());

        // Save the updated reservation
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
