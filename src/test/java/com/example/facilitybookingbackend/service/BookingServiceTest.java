package com.example.facilitybookingbackend.service;

import com.example.facilitybookingbackend.entity.Booking;
import com.example.facilitybookingbackend.exception.BookingNotFoundException;
import com.example.facilitybookingbackend.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_setsStatusToPending_andSaves() {
        Booking booking = new Booking();
        booking.setStatus("APPROVED");

        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking saved = bookingService.createBooking(booking);

        assertThat(saved.getStatus()).isEqualTo("PENDING");
        verify(bookingRepository).save(booking);
    }

    @Test
    void getAllBookings_returnsRepositoryResults() {
        Booking b1 = new Booking();
        Booking b2 = new Booking();

        when(bookingRepository.findAll()).thenReturn(List.of(b1, b2));

        List<Booking> bookings = bookingService.getAllBookings();

        assertThat(bookings).containsExactly(b1, b2);
        verify(bookingRepository).findAll();
    }

    @Test
    void getBookingsByUser_returnsRepositoryResults() {
        Booking booking = new Booking();

        when(bookingRepository.findByUserName("alice")).thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getBookingsByUser("alice");

        assertThat(bookings).containsExactly(booking);
        verify(bookingRepository).findByUserName("alice");
    }

    @Test
    void approveBooking_setsStatusToApproved_andSaves() {
        long id = 1L;
        Booking booking = new Booking();
        booking.setStatus("PENDING");

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking saved = bookingService.approveBooking(id);

        assertThat(saved.getStatus()).isEqualTo("APPROVED");
        verify(bookingRepository).save(booking);
    }

    @Test
    void approveBooking_throwsWhenNotFound() {
        long id = 404L;

        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.approveBooking(id))
            .isInstanceOf(BookingNotFoundException.class)
            .hasMessageContaining(String.valueOf(id));

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void cancelBooking_setsStatusToCancelled_andSaves() {
        long id = 2L;
        Booking booking = new Booking();
        booking.setStatus("PENDING");

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking saved = bookingService.cancelBooking(id);

        assertThat(saved.getStatus()).isEqualTo("CANCELLED");
        verify(bookingRepository).save(booking);
    }

    @Test
    void cancelBooking_throwsWhenNotFound() {
        long id = 405L;

        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.cancelBooking(id))
            .isInstanceOf(BookingNotFoundException.class)
            .hasMessageContaining(String.valueOf(id));

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void deleteBooking_deletesWhenExists() {
        long id = 3L;

        when(bookingRepository.existsById(id)).thenReturn(true);

        bookingService.deleteBooking(id);

        verify(bookingRepository).deleteById(id);
    }

    @Test
    void deleteBooking_throwsWhenNotFound() {
        long id = 406L;

        when(bookingRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> bookingService.deleteBooking(id))
            .isInstanceOf(BookingNotFoundException.class)
            .hasMessageContaining(String.valueOf(id));

        verify(bookingRepository, never()).deleteById(any());
    }
}
