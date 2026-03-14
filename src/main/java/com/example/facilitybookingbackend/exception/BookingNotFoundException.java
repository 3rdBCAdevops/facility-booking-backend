package com.example.facilitybookingbackend.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long id) {
        super("Booking not found: " + id);
    }
}
