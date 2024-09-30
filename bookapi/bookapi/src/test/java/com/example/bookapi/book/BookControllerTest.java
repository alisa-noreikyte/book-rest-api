package com.example.bookapi.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void testFindAll() throws Exception {
        Book book1 = new Book(1, "Book 1", "Author 1", 2021, 4, Status.FINISHED);
        Book book2 = new Book(2, "Book 2", "Author 2", 2022, 5, Status.FINISHED);

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"))
                .andDo(print());
    }

    @Test
    void testFindById() throws Exception {
        Book book = new Book(1, "Book 1", "Author 1", 2021, 4, Status.IN_PROGRESS);

        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andDo(print());
    }

    @Test
    void testCreateBook() throws Exception {
        Book book = new Book(1, "New Book", "Author", 2022, 5, Status.FINISHED);
        doNothing().when(bookRepository).create(Mockito.any(Book.class));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"title\": \"New Book\", \"author\": \"Author\", \"year\": 2022, \"rating\": 5, \"status\": \"FINISHED\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(bookRepository, times(1)).create(any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookRepository).delete(anyInt());

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(bookRepository, times(1)).delete(anyInt());
    }
}
