package com.library;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.library.Entities.Bookings;
import com.library.Entities.Books;
import com.library.Entities.Users;
import com.library.controller.BookingsRestApi;
import com.library.Entities.BookingStatus;
import com.library.service.BookingsService;

@ExtendWith(MockitoExtension.class)
class BookingsRestApiTest {

    @Mock
    private BookingsService bookingsService;

    @InjectMocks
    private BookingsRestApi bookingsRestApi;

    private Bookings booking;

    @BeforeEach
    void setUp() {
        booking = new Bookings();
        booking.setId(1L);
        booking.setUser(new Users());		
        booking.setBook(new Books());
        booking.setStatus(BookingStatus.active);
        booking.setBooking_date(new Date());
    }

    @Test
    void testGetAllBookings() {
        when(bookingsService.findAllBookings()).thenReturn(Arrays.asList(booking));
        List<Bookings> result = bookingsRestApi.getAll();
        assertEquals(1, result.size());
        verify(bookingsService, times(1)).findAllBookings();
    }

    @Test
    void testGetBookingById() {
        when(bookingsService.findById(1L)).thenReturn(booking);
        Bookings result = bookingsRestApi.getBookings(1L);
        assertEquals(1L, result.getId());
        verify(bookingsService, times(1)).findById(1L);
    }

    @Test
    void testSaveBooking() {
        when(bookingsService.saveBookings(any(Bookings.class))).thenReturn(booking);
        Bookings result = bookingsRestApi.saveBookings(booking);
        assertNotNull(result);
        verify(bookingsService, times(1)).saveBookings(any(Bookings.class));
    }

    @Test
    void testUpdateBooking() {
        when(bookingsService.saveBookings(any(Bookings.class))).thenReturn(booking);
        Bookings result = bookingsRestApi.updateBookings(booking);
        assertNotNull(result);
        verify(bookingsService, times(1)).saveBookings(any(Bookings.class));
    }

    @Test
    void testUpdateBookingById() {
        when(bookingsService.findById(1L)).thenReturn(booking);
        when(bookingsService.saveBookings(any(Bookings.class))).thenReturn(booking);
        Bookings updatedBooking = bookingsRestApi.updateBook1(1L, booking);
        assertNotNull(updatedBooking);
        verify(bookingsService, times(1)).findById(1L);
        verify(bookingsService, times(1)).saveBookings(any(Bookings.class));
    }

    @Test
    void testUpdateBookingAvailability() {
        when(bookingsService.findById(1L)).thenReturn(booking);
        when(bookingsService.saveBookings(any(Bookings.class))).thenReturn(booking);
        Bookings updatedBooking = bookingsRestApi.updateBook2(1L, booking);
        assertNotNull(updatedBooking);
        verify(bookingsService, times(1)).findById(1L);
        verify(bookingsService, times(1)).saveBookings(any(Bookings.class));
    }

    @Test
    void testDeleteBooking() {
        when(bookingsService.findById(1L)).thenReturn(booking);
        doNothing().when(bookingsService).deleteBookings(any(Bookings.class));
        bookingsRestApi.deleteBook(1L, booking);
        verify(bookingsService, times(1)).findById(1L);
        verify(bookingsService, times(1)).deleteBookings(any(Bookings.class));
    }
}
