package com.library.service;

import java.util.List;

import com.library.Entities.Books;

public interface BooksService {

	public Books saveUser(Books books);
	public List<Books> findAllBooks();
	public Books findById(long id);
	public void deleteUser(Books books);
}
