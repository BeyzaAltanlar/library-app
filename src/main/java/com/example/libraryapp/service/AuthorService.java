package com.example.libraryapp.service;

import com.example.libraryapp.entity.Author;
import com.example.libraryapp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String name) {
        Author author = new Author();
        author.setAuthorNameSurname(name);
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
