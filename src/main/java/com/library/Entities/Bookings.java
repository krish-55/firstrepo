package com.library.Entities;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;  
import jakarta.persistence.EnumType;  
import jakarta.persistence.ManyToOne;  
import jakarta.persistence.JoinTable;  
import jakarta.persistence.JoinColumn; 

@Entity
public class Bookings {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	@ManyToOne  
    @JoinColumn(name = "user_id", nullable = false)  
	private Users user;
	@ManyToOne  
    @JoinColumn(name = "book_id", nullable = false)  
	private Books book;
	@Enumerated(EnumType.STRING)  
	private BookingStatus status;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date booking_date;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Books getBook() {
		return book;
	}
	public void setBook(Books book) {
		this.book = book;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	public Date getBooking_date() {
		return booking_date;
	}
	public void setBooking_date(Date booking_date) {
		this.booking_date = booking_date;
	}
	
}
