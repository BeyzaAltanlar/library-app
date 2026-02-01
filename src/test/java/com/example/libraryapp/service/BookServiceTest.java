package com.example.libraryapp.service;


import com.example.libraryapp.dto.BookRequestDto;
import com.example.libraryapp.entity.Author;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Publisher;
import com.example.libraryapp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherService publisherService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBook_shouldSaveBook() {
        BookRequestDto dto = new BookRequestDto();
        dto.setTitle("Test Book");
        dto.setPrice(50.0);
        dto.setIsbn13("1234567890123");
        dto.setPublishedYear(2024);
        dto.setPublisherName("Test Publisher");
        dto.setAuthorNameSurname("John Doe");

        Publisher publisher = new Publisher();
        publisher.setPublisherName(dto.getPublisherName());

        Author author = new Author();
        author.setAuthorNameSurname(dto.getAuthorNameSurname());

        Book savedBook = new Book();
        savedBook.setTitle(dto.getTitle());
        savedBook.setPublisher(publisher);
        savedBook.setAuthor(author);

        when(publisherService.getOrCreatePublisher(dto.getPublisherName())).thenReturn(publisher);
        when(authorService.createAuthor(dto.getAuthorNameSurname())).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.createBook(dto);

        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals("Test Publisher", result.getPublisher().getPublisherName());
        assertEquals("John Doe", result.getAuthor().getAuthorNameSurname());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void getBooksStartingWithA_shouldFilterCorrectly() {
        Book book1 = new Book();
        book1.setTitle("Alpha");
        Book book2 = new Book();
        book2.setTitle("Beta");

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.getBooksStartingWithA();

        assertEquals(1, result.size());
        assertEquals("Alpha", result.get(0).getTitle());
    }

    @Test
    void getBooksAfter2023_shouldReturnCorrectBooks() {
        Book book1 = new Book();
        book1.setPublishedYear(2024);
        Book book2 = new Book();
        book2.setPublishedYear(2022);

        when(bookRepository.findByPublishedYearAfter(2023)).thenReturn(List.of(book1));

        List<Book> result = bookService.getBooksAfter2023();

        assertEquals(1, result.size());
        assertEquals(2024, result.get(0).getPublishedYear());
    }
}
