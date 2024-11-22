package com.solo.backend.service;

import com.solo.backend.dto.ReservationDTO;
import com.solo.backend.model.Reservation;
import com.solo.backend.model.User;
import com.solo.backend.model.Hotel;
import com.solo.backend.repository.ReservationRepository;
import com.solo.backend.repository.UserRepository;
import com.solo.backend.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    // Constructor injection
    public ReservationService(ReservationRepository reservationRepository, 
                              UserRepository userRepository, 
                              HotelRepository hotelRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    // Create a new reservation
    public Reservation createReservation(ReservationDTO dto) {
        // Find the user and hotel by their respective IDs
        User user = userRepository.findById(dto.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                                     .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Create a new Reservation object and set its fields
        Reservation reservation = new Reservation(null, user, hotel, dto.getCheckInDate(), dto.getCheckOutDate(), dto.getStatus());

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
    public Reservation updateReservation(Long id, ReservationDTO dto) {
        Reservation reservation = reservationRepository.findById(id)
                                                      .orElseThrow(() -> new RuntimeException("Reservation not found"));
        User user = userRepository.findById(dto.getUserId())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        Hotel hotel = hotelRepository.findById(dto.getHotelId())
                                     .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Update reservation details
        reservation.setUser(user);
        reservation.setHotel(hotel);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());
        reservation.setStatus(dto.getStatus());

        // Save the updated reservation
        return reservationRepository.save(reservation);
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
