package com.bschool.loan.repository;

import com.bschool.loan.Loan;
import com.bschool.loan.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByEndDateBefore(LocalDate maxEndDate);

    @Query("SELECT lo FROM Loan lo INNER JOIN lo.pk.customer c WHERE UPPER(c.email) = UPPER(?1) AND lo.status = ?2")
    List<Loan> getAllOpenLoansOfThisCustomer(String email, LoanStatus status);

    @Query("SELECT lo FROM Loan lo INNER JOIN lo.pk.book b INNER JOIN lo.pk.customer c WHERE b.id = ?1 AND c.id = ?2 AND  lo.status = ?3")
    Loan getLoanByCriteria(Integer bookId, Integer customerId, LoanStatus status);

}
