package com.example.bookapi.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;


public record Book(Integer id,
                   @NotEmpty
                   String title,
                   String author,
                   @Positive
                   Integer year,
                   Integer rating,
                   Status status) {
}
