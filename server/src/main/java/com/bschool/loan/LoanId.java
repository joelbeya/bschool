package com.bschool.loan;

import com.bschool.book.Book;
import com.bschool.customer.Customer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Embeddable
public class LoanId implements Serializable {

    @Serial
    private static final long serialVersionUID = -501284658043691011L;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Customer customer;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    public LoanId(Book book, Customer customer) {
        super();
        this.book = book;
        this.customer = customer;
        this.creationDateTime = LocalDateTime.now();
    }

    public LoanId() {
        super();
    }
}
