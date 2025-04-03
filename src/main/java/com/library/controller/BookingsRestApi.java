package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.Entities.Bookings;
import com.library.service.BookingsService;
@RestController
@RequestMapping("/bookings")
public class BookingsRestApi {

	@Autowired
	private BookingsService bookingsingsService;
	
    	// get all bookings details
		@GetMapping("/")
		public List<Bookings> getAll()
		{
			return bookingsingsService.findAllBookings();
		}
		
		// get single bookings details
		@GetMapping("/{id}")
		public Bookings getBookings(@PathVariable("id") long id)
		{
			return bookingsingsService.findById(id);
		}
		
		// save the bookings details
		@PostMapping("/")
		public Bookings saveBookings(@RequestBody Bookings bookings)
		{
			return bookingsingsService.saveBookings(bookings);
		}
		
		// update the bookings details using bookings object
		@PutMapping("/")
		public Bookings updateBookings(@RequestBody Bookings bookings)
		{
			return bookingsingsService.saveBookings(bookings);
		}
		
		// update the bookings details using bookings id
		@PutMapping("/{id}")
		public Bookings updateBook1(@PathVariable("id") long id,@RequestBody Bookings bookings)
		{
			Bookings b=bookingsingsService.findById(id);
			b.setUser(bookings.getUser());
			b.setBook(bookings.getBook());
			b.setStatus(bookings.getStatus());
			b.setBooking_date(bookings.getBooking_date());
			return bookingsingsService.saveBookings(b);
		}
		
		// update the only availability in bookings entity 
			@PatchMapping("/{id}")
			public Bookings updateBook2(@PathVariable("id") long id,@RequestBody Bookings bookings)
			{
				Bookings b=bookingsingsService.findById(id);
				b.setBooking_date(bookings.getBooking_date());
				return bookingsingsService.saveBookings(b);
			}
		
		// delete the bookings details
		@DeleteMapping("/{id}")
		public void deleteBook(@PathVariable("id") long id,@RequestBody Bookings bookings)
		{
			Bookings b=bookingsingsService.findById(id);
			bookingsingsService.deleteBookings(b);
		}
}
