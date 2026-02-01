package com.example.libraryapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorID;

    private String authorNameSurname;

    @OneToOne
    @JoinColumn(name = "bookID")
    @JsonIgnore
    private Book book;
}
