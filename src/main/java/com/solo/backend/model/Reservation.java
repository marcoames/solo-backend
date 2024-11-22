package com.solo.backend.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generates ID values
    private Long id;  

    @ManyToOne  // Many reservations can belong to one user
    private User user;

    @ManyToOne  // Many reservations can belong to one hotel
    private Hotel hotel;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;

    // Default constructor
    public Reservation() {
    }

    // Constructor with parameters
    public Reservation(Long id, User user, Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.id = id;
        this.user = user;
        this.hotel = hotel;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for hotel
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // Getter and Setter for checkInDate
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    // Getter and Setter for checkOutDate
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: Override toString, equals, and hashCode if needed
    @Override
    public String toString() {
        return "Reservation{id=" + id + ", user=" + user + ", hotel=" + hotel + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", status='" + status + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation that = (Reservation) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31 * (id != null ? id.hashCode() : 0);
    }
}
