package com.bschool.loan.controller;

import com.bschool.book.Book;
import com.bschool.book.controller.BookRestController;
import com.bschool.customer.Customer;
import com.bschool.loan.Loan;
import com.bschool.loan.LoanId;
import com.bschool.loan.LoanStatus;
import com.bschool.loan.SimpleLoan;
import com.bschool.loan.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/rest/api/loan")
public class LoanRestController {

    public static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    private final LoanRepository loanRepository;

    public LoanRestController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @GetMapping(value = "maxEndDate/{maxEndDate}")
    public ResponseEntity<List<Loan>> searchAllBooksLoanBeforeThisDate(@PathVariable String maxEndDate) {
        List<Loan> loans = loanRepository.findByEndDateBefore(LocalDate.parse(maxEndDate));
        loans.removeAll(Collections.singleton(null));
        logger.info("List loans realized before the indicated date");
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping(value = "/customersLoans/{email}")
    public ResponseEntity<List<Loan>> searchAllOpenedLoansOfThisCustomer(@PathVariable String email) {
        List<Loan> loans = loanRepository.getAllOpenLoansOfThisCustomer(email, LoanStatus.OPEN);
        loans.removeAll(Collections.singleton(null));
        logger.info("List loans realized before the indicated date of {} customer", email);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    public ResponseEntity<Boolean> createLoan(@RequestBody SimpleLoan simpleLoan) {
        Loan loan = loanRepository.getLoanByCriteria(simpleLoan.getBookId(), simpleLoan.getCustomerId(), LoanStatus.OPEN);
        if (loan == null) {
            Loan _loan = new Loan();
            Book _book = new Book();
            Customer _customer = new Customer();
            _book.setId(simpleLoan.getBookId());
            _customer.setId(simpleLoan.getCustomerId());
            LoanId _loanId = new LoanId(_book, _customer);
            _loan.setPk(_loanId);
            _loan.setBeginDate(simpleLoan.getBeginDate());
            _loan.setEndDate(simpleLoan.getEndDate());
            _loan.setStatus(LoanStatus.OPEN);
            loanRepository.save(_loan);
            logger.info("Loan {} added properly", _loan);
            return new ResponseEntity<>(false, HttpStatus.OK);
        } else logger.info("Loan {} already exist", loan);
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    @PostMapping(value = "close")
    public ResponseEntity<Boolean> closeLoan(@RequestBody SimpleLoan simpleLoan) {
        Loan loan = loanRepository.getLoanByCriteria(
                simpleLoan.getBookId(), simpleLoan.getCustomerId(), LoanStatus.OPEN);
        if (loan == null) {
            logger.error("Loan {} doesn't exist", (Object) null);
            return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
        }
        loan.setStatus(LoanStatus.CLOSE);
        loanRepository.save(loan);
        logger.info("Loan {} closed properly", loan);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
