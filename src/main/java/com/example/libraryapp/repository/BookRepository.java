package com.example.libraryapp.repository;

import com.example.libraryapp.entity.Book;
import com.example.libraryapp.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 2023 sonrasi kitaplari getir
    List<Book> findByPublishedYearAfter(Integer year);

    // Belirli yayinevlerine ait kitaplari getir
    List<Book> findByPublisherIn(List<Publisher> publishers);
}
