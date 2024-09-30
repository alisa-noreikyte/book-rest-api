package com.example.bookapi.book;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final JdbcClient jdbcClient;

    public BookRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Book> findAll() {
        return jdbcClient.sql("select * from book")
                .query(Book.class)
                .list();
    }

    public Optional<Book> findById(Integer id) {
        return jdbcClient.sql("SELECT id,title,author,year,rating,status FROM Book WHERE id = :id" )
                .param("id", id)
                .query(Book.class)
                .optional();
    }

    public void create(Book book) {
        var updated = jdbcClient.sql("INSERT INTO Book(id,title,author,year,rating,status) values(?,?,?,?,?,?)")
                .params(List.of(book.id(),book.title(),book.author(),book.year(),book.rating(),book.status().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create book " + book.title());
    }

    // Update book rating
    public void update(Book book, Integer id) {
        var updated = jdbcClient.sql("update book set rating = ? where id = ?")
                .params(List.of(book.rating().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update book " + book.title() + " rating");
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from book where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete book " + id);
    }

    public int count() {
        return jdbcClient.sql("select * from book").query().listOfRows().size();
    }

    public void saveAll(List<Book> books) {
        books.stream().forEach(this::create);
    }

    // Filtering by title
    public Optional<Book> findByTitle(String title) {
        return jdbcClient.sql("select * from book where title = :title")
                .param("title", title)
                .query(Book.class)
                .optional();
    }

    // Filtering by year
    public Optional<Book> findByYear(Integer year) {
        return jdbcClient.sql("select * from book where year = :year")
                .param("year", year)
                .query(Book.class)
                .optional();
    }

    // Filtering by status
    public Optional<Book> findByStatus(String status) {
        return jdbcClient.sql("select * from book where status = :status")
                .param("status", status)
                .query(Book.class)
                .optional();
    }

    // Filtering by author
    public Optional<Book> findByAuthor(String author) {
        return jdbcClient.sql("select * from book where author = :author")
                .param("author", author)
                .query(Book.class)
                .optional();
    }

    // Filtering by rating
    public Optional<Book> findByRating(Integer rating) {
        return jdbcClient.sql("select * from book where rating = :rating")
                .param("rating", rating)
                .query(Book.class)
                .optional();
    }

}
