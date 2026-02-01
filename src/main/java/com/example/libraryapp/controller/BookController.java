package com.example.libraryapp.controller;

import com.example.libraryapp.dto.BookRequestDto;
import com.example.libraryapp.entity.Book;
import com.example.libraryapp.service.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET ALL
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // CREATE
    @PostMapping
    public Book createBook(@RequestBody BookRequestDto dto) {
        return bookService.createBook(dto);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody BookRequestDto dto) {
        return bookService.updateBook(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // Bas harfi 'A' olan kitaplar
    @GetMapping("/starts-with-a")
    public List<Book> getBooksStartingWithA() {
        return bookService.getBooksStartingWithA();
    }

    // 2023 sonrasi kitaplar
    @GetMapping("/after2023")
    public List<Book> getBooksAfter2023() {
        return bookService.getBooksAfter2023();
    }
    // İlk 2 yayınevindeki kitap ve yazar listele
    @GetMapping("/top2-publishers")
    public List<Book> getBooksOfTop2Publishers() {
        return bookService.getBooksOfTwoPublishers();
    }

}
