package com.bschool.book.repository;

import com.bschool.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByIsbn(String isbn);

    Book findByIsbnContainingIgnoreCase(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT b FROM Book b INNER JOIN b.category cat WHERE cat.code = :code")
    List<Book> findByCategory(@Param("code") String codeCategory);

}
