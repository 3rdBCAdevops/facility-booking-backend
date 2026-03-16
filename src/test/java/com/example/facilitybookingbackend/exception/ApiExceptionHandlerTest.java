package com.example.facilitybookingbackend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiExceptionHandlerTest {

    @Test
    void handleBookingNotFound_returns404BodyWithPath() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/bookings/99");

        ResponseEntity<Map<String, Object>> response = handler.handleBookingNotFound(
            new BookingNotFoundException(99L),
            request
        );

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody())
            .containsEntry("status", 404)
            .containsEntry("error", "Not Found")
            .containsEntry("message", "Booking not found: 99")
            .containsEntry("path", "/api/bookings/99");
        assertThat(response.getBody().get("timestamp")).isInstanceOf(String.class);
    }

    @Test
    void handleEmptyResult_returns404BodyWithGenericMessage() {
        ApiExceptionHandler handler = new ApiExceptionHandler();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/bookings/123");

        ResponseEntity<Map<String, Object>> response = handler.handleEmptyResult(
            new EmptyResultDataAccessException(1),
            request
        );

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody())
            .containsEntry("status", 404)
            .containsEntry("error", "Not Found")
            .containsEntry("message", "Booking not found")
            .containsEntry("path", "/api/bookings/123");
        assertThat(response.getBody().get("timestamp")).isInstanceOf(String.class);
    }
}
