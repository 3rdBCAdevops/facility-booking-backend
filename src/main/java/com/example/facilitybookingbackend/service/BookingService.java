package com.example.facilitybookingbackend.service;

import com.example.facilitybookingbackend.entity.Booking;
import com.example.facilitybookingbackend.exception.BookingNotFoundException;
import com.example.facilitybookingbackend.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // CREATE
    public Booking createBooking(Booking booking) {
        booking.setStatus("PENDING");
        return bookingRepository.save(booking);
    }

    // READ - Admin: all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // READ - Student: own bookings
    public List<Booking> getBookingsByUser(String userName) {
        return bookingRepository.findByUserName(userName);
    }

    // UPDATE - Admin approve
    public Booking approveBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        booking.setStatus("APPROVED");
        return bookingRepository.save(booking);
    }

    // UPDATE - Admin cancel
    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    // DELETE - Student delete booking
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new BookingNotFoundException(id);
        }
        bookingRepository.deleteById(id);
    }
}
