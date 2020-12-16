package com.bschool.customer;

import com.bschool.loan.Loan;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "customer")
public class Customer implements Serializable {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.customer", cascade = CascadeType.ALL)
    Set<Loan> loans = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "job")
    private String job;
    @Column(name = "address")
    private String address;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

}
