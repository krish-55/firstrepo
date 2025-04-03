package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.Entities.Bookings;
import com.library.Entities.Books;
import com.library.Entities.Users;
import com.library.Repository.BookingsRepository;
import com.library.Repository.BooksRepository;
import com.library.Repository.UserRepository;

@Service
public class BookingsServiceImpl implements BookingsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BooksRepository booksRepository;
	@Autowired
	private BookingsRepository bookingsRepository;
	@Override
	public Bookings saveBookings(Bookings bookings) {
		Users u=userRepository.findById(bookings.getUser().getId()).orElse(null);
		Books b=booksRepository.findById(bookings.getBook().getId()).orElse(null);
		
		 // Check if the user and book are valid  
	    if (u == null || b == null) {  
	        throw new IllegalArgumentException("Invalid user or book details");  
	    }  
	    
	    // Set the fetched entities back to the bookings object  
	    bookings.setUser(u);  
	    bookings.setBook(b);  
	    
		return bookingsRepository.save(bookings);
	}

	@Override
	public List<Bookings> findAllBookings() {
		return bookingsRepository.findAll();
	}

	@Override
	public Bookings findById(long id) {
		return bookingsRepository.findById(id).get();
	}

	@Override
	public void deleteBookings(Bookings bookings) {
		bookingsRepository.delete(bookings);

	}

}
