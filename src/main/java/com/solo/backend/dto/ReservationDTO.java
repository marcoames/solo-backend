package com.solo.backend.dto;

import java.time.LocalDate;

public class ReservationDTO {

    private Long userId;
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;

    // Default constructor
    public ReservationDTO() {
    }

    // Constructor with parameters
    public ReservationDTO(Long userId, Long hotelId, LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for hotelId
    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
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
        return "ReservationDTO{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReservationDTO that = (ReservationDTO) obj;
        return userId.equals(that.userId) && hotelId.equals(that.hotelId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + hotelId.hashCode();
    }
}
