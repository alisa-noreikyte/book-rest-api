package com.example.bookapi.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity("/api/books", Book[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testCreateBook() {
        Book book = new Book(3, "Integration Book", "Author 3", 2021, 4, Status.FINISHED);
        HttpEntity<Book> request = new HttpEntity<>(book);

        ResponseEntity<Void> response = restTemplate.postForEntity("/api/books", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testDeleteBook() {
        int bookId = 1; // assuming this book exists in your test data
        restTemplate.delete("/api/books/" + bookId);

        ResponseEntity<Book> response = restTemplate.getForEntity("/api/books/" + bookId, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
