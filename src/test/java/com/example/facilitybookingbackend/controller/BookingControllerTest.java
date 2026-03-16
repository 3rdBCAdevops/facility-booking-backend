package com.example.facilitybookingbackend.controller;

import com.example.facilitybookingbackend.entity.Booking;
import com.example.facilitybookingbackend.exception.BookingNotFoundException;
import com.example.facilitybookingbackend.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void createBooking_returnsSavedBooking() throws Exception {
        Booking response = new Booking();
        response.setId(1L);
        response.setFacilityName("Room A");
        response.setUserName("alice");
        response.setStatus("PENDING");

        when(bookingService.createBooking(any(Booking.class))).thenReturn(response);

        Booking request = new Booking();
        request.setFacilityName("Room A");
        request.setUserName("alice");

        Booking actual = bookingController.createBooking(request);

        assertThat(actual).isSameAs(response);
        verify(bookingService).createBooking(request);
    }

    @Test
    void getAllBookings_returnsList() throws Exception {
        Booking b1 = new Booking();
        b1.setId(1L);
        b1.setStatus("PENDING");

        Booking b2 = new Booking();
        b2.setId(2L);
        b2.setStatus("APPROVED");

        when(bookingService.getAllBookings()).thenReturn(List.of(b1, b2));

        List<Booking> actual = bookingController.getAllBookings();

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getId()).isEqualTo(1L);
        assertThat(actual.get(1).getId()).isEqualTo(2L);
        verify(bookingService).getAllBookings();
    }

    @Test
    void getBookingsByUser_returnsList() throws Exception {
        Booking booking = new Booking();
        booking.setId(10L);
        booking.setUserName("bob");

        when(bookingService.getBookingsByUser("bob")).thenReturn(List.of(booking));

        List<Booking> actual = bookingController.getBookingsByUser("bob");

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getId()).isEqualTo(10L);
        verify(bookingService).getBookingsByUser("bob");
    }

    @Test
    void approveBooking_returnsBooking() throws Exception {
        Booking booking = new Booking();
        booking.setId(5L);
        booking.setStatus("APPROVED");

        when(bookingService.approveBooking(5L)).thenReturn(booking);

        Booking actual = bookingController.approveBooking(5L);

        assertThat(actual).isSameAs(booking);
        verify(bookingService).approveBooking(5L);
    }

    @Test
    void cancelBooking_returnsBooking() throws Exception {
        Booking booking = new Booking();
        booking.setId(6L);
        booking.setStatus("CANCELLED");

        when(bookingService.cancelBooking(6L)).thenReturn(booking);

        Booking actual = bookingController.cancelBooking(6L);

        assertThat(actual).isSameAs(booking);
        verify(bookingService).cancelBooking(6L);
    }

    @Test
    void deleteBooking_returnsMessage() throws Exception {
        String message = bookingController.deleteBooking(7L);

        assertThat(message).isEqualTo("Booking deleted successfully");
        verify(bookingService).deleteBooking(7L);
    }

    @Test
    void approveBooking_propagatesBookingNotFoundException() {
        when(bookingService.approveBooking(99L)).thenThrow(new BookingNotFoundException(99L));

        assertThatThrownBy(() -> bookingController.approveBooking(99L))
            .isInstanceOf(BookingNotFoundException.class)
            .hasMessage("Booking not found: 99");
    }
}
