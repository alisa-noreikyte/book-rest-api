package com.example.bookapi.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRepositoryTest {

    private BookRepository bookRepository;

    @Mock
    private JdbcClient jdbcClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookRepository = new BookRepository(jdbcClient);
    }

    @Test
    void testFindById() {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2023, 5, Status.FINISHED);
        when(jdbcClient.sql(anyString()).param(eq("id"), eq(1)).query(Book.class).optional())
                .thenReturn(Optional.of(book));

        // When
        Optional<Book> foundBook = bookRepository.findById(1);

        // Then
        assertTrue(foundBook.isPresent());
        assertEquals("Book Title", foundBook.get().title());
    }

    @Test
    void testCreate() {
        // Given
        Book book = new Book(1, "New Book", "Author", 2022, 4, Status.IN_PROGRESS);
        when(jdbcClient.sql(anyString()).params(anyList()).update()).thenReturn(1);

        // When
        bookRepository.create(book);

        // Then
        verify(jdbcClient, times(1)).sql(anyString());
    }

    @Test
    void testDelete() {
        // Given
        when(jdbcClient.sql(anyString()).param(eq("id"), eq(1)).update()).thenReturn(1);

        // When
        bookRepository.delete(1);

        // Then
        verify(jdbcClient, times(1)).sql(anyString());
    }
}
