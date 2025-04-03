package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.Entities.Books;
import com.library.Repository.BooksRepository;

@Service
public class BooksServiceImpl implements BooksService {

	@Autowired
	private BooksRepository booksRepository;
	
	@Override
	public Books saveUser(Books books) {
		return booksRepository.save(books);
	}

	@Override
	public List<Books> findAllBooks() {
		return booksRepository.findAll();
	}

	@Override
	public Books findById(long id) {
		return booksRepository.findById(id).get();
	}

	@Override
	public void deleteUser(Books books) {
		booksRepository.delete(books);
	}

}
