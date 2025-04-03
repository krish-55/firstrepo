package com.library.service;

import java.util.List;

import com.library.Entities.Bookings;

public interface BookingsService {

	public Bookings saveBookings(Bookings bookings);
	public List<Bookings> findAllBookings();
	public Bookings findById(long id);
	public void deleteBookings(Bookings bookings);
}
