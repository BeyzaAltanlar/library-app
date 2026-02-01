package com.example.libraryapp.service;

import com.example.libraryapp.dto.BookRequestDto;
import com.example.libraryapp.entity.Author;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Publisher;
import com.example.libraryapp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository,
                       PublisherService publisherService,
                       AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }

    public Book createBook(BookRequestDto dto) {

        Publisher publisher = publisherService.getOrCreatePublisher(dto.getPublisherName());

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setIsbn13(dto.getIsbn13());
        book.setPublishedYear(dto.getPublishedYear());
        book.setPublisher(publisher);

        Author author = authorService.createAuthor(dto.getAuthorNameSurname());
        author.setBook(book);
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    // READ BY ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // UPDATE
    public Book updateBook(Long id, BookRequestDto dto) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(dto.getTitle());
            book.setPrice(dto.getPrice());
            book.setIsbn13(dto.getIsbn13());
            book.setPublishedYear(dto.getPublishedYear());

            Publisher publisher = publisherService.getOrCreatePublisher(dto.getPublisherName());
            book.setPublisher(publisher);

            Author author = book.getAuthor();
            if (author == null) {
                author = authorService.createAuthor(dto.getAuthorNameSurname());
                author.setBook(book);
                book.setAuthor(author);
            } else {
                author.setAuthorNameSurname(dto.getAuthorNameSurname());
            }

            return bookRepository.save(book);
        }).orElse(null);
    }

    // DELETE
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    public List<Book> getBooksStartingWithA() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().startsWith("A"))
                .toList();
    }

    public List<Book> getBooksAfter2023() {
        return bookRepository.findByPublishedYearAfter(2023);
    }
    public List<Book> getBooksOfTwoPublishers() {
        // ilk iki yayinevini al
        List<Publisher> topTwoPublishers = publisherService.getTop2Publishers();

        // Yayinevlerindeki kitaplari al
        return bookRepository.findByPublisherIn(topTwoPublishers);
    }

}
