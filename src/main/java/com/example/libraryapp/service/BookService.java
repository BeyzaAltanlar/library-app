package com.example.libraryapp.service;

import com.example.libraryapp.dto.BookRequestDto;
import com.example.libraryapp.dto.BookResponseDto;
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

    public BookResponseDto createBook(BookRequestDto dto) {

        Publisher publisher =
                publisherService.getOrCreatePublisher(dto.getPublisherName());

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setIsbn13(dto.getIsbn13());
        book.setPublishedYear(dto.getPublishedYear());
        book.setPublisher(publisher);

        Author author =
                authorService.createAuthor(dto.getAuthorNameSurname());
        author.setBook(book);
        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);

        return mapToDto(savedBook);
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // READ BY ID
    public BookResponseDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }


    // UPDATE
    public BookResponseDto updateBook(Long id, BookRequestDto dto) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(dto.getTitle());
                    book.setPrice(dto.getPrice());
                    book.setIsbn13(dto.getIsbn13());
                    book.setPublishedYear(dto.getPublishedYear());

                    Publisher publisher =
                            publisherService.getOrCreatePublisher(dto.getPublisherName());
                    book.setPublisher(publisher);

                    Author author = book.getAuthor();
                    if (author == null) {
                        author = authorService.createAuthor(dto.getAuthorNameSurname());
                        author.setBook(book);
                        book.setAuthor(author);
                    } else {
                        author.setAuthorNameSurname(dto.getAuthorNameSurname());
                    }

                    return mapToDto(bookRepository.save(book));
                })
                .orElse(null);
    }

    // DELETE
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    public List<BookResponseDto> getBooksStartingWithA() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().startsWith("A"))
                .map(this::mapToDto)
                .toList();
    }


    public List<BookResponseDto> getBooksAfter2023() {
        return bookRepository.findByPublishedYearAfter(2023)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<BookResponseDto> getBooksOfTwoPublishers() {
        // ilk iki yayinevini al
        List<Publisher> topTwoPublishers =
                publisherService.getTop2Publishers();

        // Yayinevlerindeki kitaplari al
        return bookRepository.findByPublisherIn(topTwoPublishers)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private BookResponseDto mapToDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setIsbn13(book.getIsbn13());
        dto.setPublishedYear(book.getPublishedYear());

        if (book.getAuthor() != null) {
            dto.setAuthorNameSurname(book.getAuthor().getAuthorNameSurname());
        }

        if (book.getPublisher() != null) {
            dto.setPublisherName(book.getPublisher().getPublisherName());
        }

        return dto;
    }

}
