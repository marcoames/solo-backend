package com.solo.backend.repository;

import com.solo.backend.model.Hotel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);
}
