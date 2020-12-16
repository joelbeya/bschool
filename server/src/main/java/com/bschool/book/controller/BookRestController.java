package com.bschool.book.controller;

import com.bschool.book.Book;
import com.bschool.book.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/rest/api/book")
public class BookRestController {

    public static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping(value = "create")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book _book = bookRepository.findByIsbn(book.getIsbn());
        if (_book != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        _book = bookRepository.save(book);
        if (_book.getId() != null) {
            logger.info("Book {} created properly", _book.getTitle());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else logger.error("Book {} not created", _book.getTitle());
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "update")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if (!bookRepository.existsById(book.getId())) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Book _book = bookRepository.save(book);
        if (_book.getId() != null) {
            logger.info("Book {} updated properly", _book.getTitle());
            return new ResponseEntity<>(HttpStatus.OK);
        } else logger.error("Book {} not updated", _book.getTitle());
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "delete/{bookId}")
    public ResponseEntity<String> delete(@PathVariable Integer bookId) {
        bookRepository.deleteById(bookId);
        logger.info("Book with id {} deleted properly", bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            logger.info("Get all books : {} ", books.toString());
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        return new ResponseEntity<>(books, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "searchBy/{title}")
    public ResponseEntity<List<Book>> searchBooksByTitle(@PathVariable String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if (!books.isEmpty()) {
            logger.info("Book(s) with title {} found {}", title, books);
            return new ResponseEntity<>(books, HttpStatus.FOUND);
        } else logger.error("Book(s) with title {} not found", title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "searchBy/{isbn}")
    public ResponseEntity<Book> searchBooksByIsbn(@PathVariable String isbn) {
        Book _book = bookRepository.findByIsbnContainingIgnoreCase(isbn);
        if (_book != null) {
            logger.info("Book by isbn {} found", isbn);
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else logger.error("Book(s) by isbn {} not found", isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "searchBy/{category}")
    public ResponseEntity<List<Book>> searchBooksByCategory(@PathVariable String category) {
        List<Book> _book = bookRepository.findByCategory(category);
        if (_book != null) {
            logger.info("Book(s) with category {} found", category);
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else logger.error("Book(s) by isbn {} not found", category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
