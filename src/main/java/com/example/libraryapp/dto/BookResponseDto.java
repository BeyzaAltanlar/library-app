package com.example.libraryapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookResponseDto {

    // getters & setters
    private Long id;
    private String title;
    private Double price;
    private String isbn13;
    private Integer publishedYear;
    private String authorNameSurname;
    private String publisherName;

}
