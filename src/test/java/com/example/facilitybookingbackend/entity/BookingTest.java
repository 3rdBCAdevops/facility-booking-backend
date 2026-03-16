package com.example.facilitybookingbackend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookingTest {

    @Test
    void gettersAndSetters_roundTripAllFields() {
        Booking booking = new Booking();

        LocalDate date = LocalDate.of(2026, 3, 16);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 30);

        booking.setId(123L);
        booking.setFacilityName("Room A");
        booking.setFacilityType("CLASSROOM");
        booking.setUserName("alice");
        booking.setUserRole("STUDENT");
        booking.setBookingDate(date);
        booking.setStartTime(start);
        booking.setEndTime(end);
        booking.setStatus("PENDING");

        assertThat(booking.getId()).isEqualTo(123L);
        assertThat(booking.getFacilityName()).isEqualTo("Room A");
        assertThat(booking.getFacilityType()).isEqualTo("CLASSROOM");
        assertThat(booking.getUserName()).isEqualTo("alice");
        assertThat(booking.getUserRole()).isEqualTo("STUDENT");
        assertThat(booking.getBookingDate()).isEqualTo(date);
        assertThat(booking.getStartTime()).isEqualTo(start);
        assertThat(booking.getEndTime()).isEqualTo(end);
        assertThat(booking.getStatus()).isEqualTo("PENDING");
    }
}
