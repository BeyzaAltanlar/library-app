package com.example.libraryapp.repository;

import com.example.libraryapp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByAuthorNameSurname(String authorNameSurname);
}
