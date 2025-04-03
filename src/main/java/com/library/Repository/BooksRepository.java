package com.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.Entities.Books;

public interface BooksRepository extends JpaRepository<Books, Long> {

}
