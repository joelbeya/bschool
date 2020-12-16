package com.bschool.book;

import com.bschool.category.Category;
import com.bschool.loan.Loan;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "book")
public class Book {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.book", cascade = CascadeType.ALL)
    Set<Loan> loans = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;
    @Column(name = "total_examplars")
    private Integer totalExemplars;
    @Column(name = "author")
    private String author;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cat_code", referencedColumnName = "code")
    private Category category;

}
