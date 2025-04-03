package com.library;
import static org.junit.jupiter.api.Assertions.assertEquals;  
import static org.mockito.ArgumentMatchers.any;  
import static org.mockito.Mockito.doNothing;  
import static org.mockito.Mockito.verify;  
import static org.mockito.Mockito.when;  

import java.util.Arrays;  
import java.util.List;  

import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;  
import org.mockito.InjectMocks;  
import org.mockito.Mock;  
import org.mockito.MockitoAnnotations;  
import org.springframework.http.HttpStatus;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.RequestBody;  

import com.library.Entities.Books;
import com.library.controller.BooksRestApi;
import com.library.service.BooksService;  

class BooksRestApiTest {  

    @InjectMocks  
    private BooksRestApi booksRestApi;  

    @Mock  
    private BooksService booksService;  

    private Books book1;  
    private Books book2;  

    @BeforeEach  
    public void setUp() {  
        MockitoAnnotations.openMocks(this);  

        book1 = new Books(1L, "Book One", "Author One", "Category One", true);  
        book2 = new Books(2L, "Book Two", "Author Two", "Category Two", false);  
    }  

    @Test  
    public void testGetAllBooks() {  
        when(booksService.findAllBooks()).thenReturn(Arrays.asList(book1, book2));  

        List<Books> books = booksRestApi.getAll();  
        assertEquals(2, books.size());  
        assertEquals("Book One", books.get(0).getTitle());  
    }  

    @Test  
    public void testGetBook() {  
        when(booksService.findById(1L)).thenReturn(book1);  

        Books book = booksRestApi.getBook(1L);  
        assertEquals("Book One", book.getTitle());  
        verify(booksService).findById(1L);  
    }  

    @Test  
    public void testSaveBook() {  
        when(booksService.saveUser(any(Books.class))).thenReturn(book1);  

        Books savedBook = booksRestApi.saveBook(book1);  
        assertEquals("Book One", savedBook.getTitle());  
        verify(booksService).saveUser(any(Books.class));  
    }  

    @Test  
    public void testUpdateBook() {  
        when(booksService.saveUser(any(Books.class))).thenReturn(book1);  

        Books updatedBook = booksRestApi.updateBook(book1);  
        assertEquals("Book One", updatedBook.getTitle());  
        verify(booksService).saveUser(any(Books.class));  
    }  

    @Test  
    public void testUpdateBookById() {  
        when(booksService.findById(1L)).thenReturn(book1);  
        when(booksService.saveUser(any(Books.class))).thenReturn(book1);  

        Books updatedBook = booksRestApi.updateBook1(1L, book1);  
        assertEquals("Book One", updatedBook.getTitle());  
        verify(booksService).findById(1L);  
        verify(booksService).saveUser(any(Books.class));  
    }  

    @Test  
    public void testUpdateBookAvailability() {  
        when(booksService.findById(1L)).thenReturn(book1);  
        when(booksService.saveUser(any(Books.class))).thenReturn(book1);  

        book1.setAvailability(false); // Change availability  
        Books updatedBook = booksRestApi.updateBook2(1L, book1);  
        assertEquals(false, updatedBook.getAvailability());  
        verify(booksService).findById(1L);  
        verify(booksService).saveUser(any(Books.class));  
    }  

    @Test  
    public void testDeleteBook() {  
        when(booksService.findById(1L)).thenReturn(book1);  
        doNothing().when(booksService).deleteUser(any(Books.class));  

        booksRestApi.deleteBook(1L, book1);  
        verify(booksService).findById(1L);  
        verify(booksService).deleteUser(book1);  
    }  
}  
