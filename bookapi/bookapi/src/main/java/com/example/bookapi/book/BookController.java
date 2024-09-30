package com.example.bookapi.book;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    List<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    Book findById(@PathVariable Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()) {
            throw new BookNotFoundException();
        }
        return book.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void create(@Valid @RequestBody Book book) {
        bookRepository.create(book);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Book book, @PathVariable Integer id) {
        bookRepository.update(book,id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        bookRepository.delete(id);
    }

    @GetMapping("/{title}")
    Book findByTitle(@PathVariable String title) {
        Optional<Book> run1 = bookRepository.findByTitle(title);
        if(run1.isEmpty()) {
            throw new BookNotFoundException();
        }
        return run1.get();
    }

    @GetMapping("/{year}")
    Book findByYear(@PathVariable Integer year) {
        Optional<Book> run2 = bookRepository.findByYear(year);
        if(run2.isEmpty()) {
            throw new BookNotFoundException();
        }
        return run2.get();
    }

    @GetMapping("/{status}")
    Book findByStatus(@PathVariable String status) {
        Optional<Book> run3 = bookRepository.findByStatus(status);
        if(run3.isEmpty()) {
            throw new BookNotFoundException();
        }
        return run3.get();
    }

    @GetMapping("/{author}")
    Book findByAuthor(@PathVariable String author) {
        Optional<Book> run4 = bookRepository.findByAuthor(author);
        if(run4.isEmpty()) {
            throw new BookNotFoundException();
        }
        return run4.get();
    }

    @GetMapping("/{rating}")
    Book findByRating(@PathVariable Integer rating) {
        Optional<Book> run5 = bookRepository.findByRating(rating);
        if(run5.isEmpty()) {
            throw new BookNotFoundException();
        }
        return run5.get();
    }

}
