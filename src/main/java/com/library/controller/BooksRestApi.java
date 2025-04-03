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

import com.library.Entities.Books;
import com.library.service.BooksService;

@RestController
@RequestMapping("/books")
public class BooksRestApi {

	@Autowired
	private BooksService booksService;
	
	// get all book details
	@GetMapping("/")
	public List<Books> getAll()
	{
		return booksService.findAllBooks();
	}
	
	// get single book details
	@GetMapping("/{id}")
	public Books getBook(@PathVariable("id") long id)
	{
		return booksService.findById(id);
	}
	
	// save the book details
	@PostMapping("/")
	public Books saveBook(@RequestBody Books book)
	{
		return booksService.saveUser(book);
	}
	
	// update the book details using book object
	@PutMapping("/")
	public Books updateBook(@RequestBody Books book)
	{
		return booksService.saveUser(book);
	}
	
	// update the book details using book id
	@PutMapping("/{id}")
	public Books updateBook1(@PathVariable("id") long id,@RequestBody Books book)
	{
		Books b=booksService.findById(id);
		b.setTitle(book.getTitle());
		b.setAuthor(book.getAuthor());
		b.setCategory(book.getCategory());
		b.setAvailability(book.getAvailability());
		return booksService.saveUser(b);
	}
	
	// update the only availability in book entity 
		@PatchMapping("/{id}")
		public Books updateBook2(@PathVariable("id") long id,@RequestBody Books book)
		{
			Books b=booksService.findById(id);
			b.setAvailability(book.getAvailability());
			return booksService.saveUser(b);
		}
	
	// delete the book details
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable("id") long id,@RequestBody Books book)
	{
		Books b=booksService.findById(id);
		booksService.deleteUser(b);
	}
}
