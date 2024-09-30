package com.example.bookapi.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class BookJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BookJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;

    public BookJsonDataLoader(ObjectMapper objectMapper, BookRepository bookRepository) {
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(bookRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/books.json")) {
                Books allBooks = objectMapper.readValue(inputStream, Books.class);
                log.info("Reading {} books from JSON data and saving it to a database.", allBooks.books().size());
                bookRepository.saveAll(allBooks.books());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Books from JSON data because the collection contains data.");
        }
    }

}
