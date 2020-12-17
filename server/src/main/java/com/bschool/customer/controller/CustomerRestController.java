package com.bschool.customer.controller;

import com.bschool.book.controller.BookRestController;
import com.bschool.customer.Customer;
import com.bschool.customer.SimpleMail;
import com.bschool.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/rest/api/customer")
public class CustomerRestController {

    public static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    private final CustomerRepository customerRepository;

    private final JavaMailSender javaMailSender;

    public CustomerRestController(CustomerRepository customerRepository, JavaMailSender javaMailSender) {
        this.customerRepository = customerRepository;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping(value = "create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer _customer = customerRepository.findCustomerByEmailContainingIgnoreCase(customer.getEmail());
        if (_customer != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        _customer = customerRepository.save(customer);
        if (_customer.getId() != null) {
            logger.info("Customer {} added properly", _customer.getFirstName() + _customer.getFirstName());
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "update")
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        if (!customerRepository.existsById(customer.getId())) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Customer _customer = customerRepository.save(customer);
        if (_customer.getId() != null) {
            logger.info("Customer {} updated properly", _customer.getLastName() + _customer.getFirstName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "delete/{customerId}")
    public ResponseEntity<String> delete(@PathVariable Integer customerId) {
        customerRepository.deleteById(customerId);
        logger.info("Customer with id {} removed properly", customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "searchByEmail/{email}")
    public ResponseEntity<Customer> searchCustomerByEmail(@PathVariable String email) {
        Customer customer = customerRepository.findCustomerByEmailContainingIgnoreCase(email);
        if (customer != null) {
            logger.info("Customer with name {} found", customer.getLastName() + customer.getFirstName());
            return new ResponseEntity<>(customer, HttpStatus.FOUND);
        } else logger.error("Customer not found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "searchByFirstName/{firstName}")
    public ResponseEntity<List<Customer>> searchCustomerByFirstName(@PathVariable String firstName) {
        List<Customer> customers = customerRepository.findCustomerByFirstNameContainingIgnoreCase(firstName);
        if (customers != null) {
            logger.info("Customer(s) with first name {} found {}", firstName, customers);
            return new ResponseEntity<>(customers, HttpStatus.FOUND);
        } else logger.error("Customers not found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "searchByLastName/{lastName}")
    public ResponseEntity<List<Customer>> searchCustomerByLastName(@PathVariable String lastName) {
        List<Customer> customers = customerRepository.findCustomerByLastNameContainingIgnoreCase(lastName);
        if (customers != null) {
            logger.info("Customer(s) with last name {} found {}", lastName, customers);
            return new ResponseEntity<>(customers, HttpStatus.FOUND);
        } else logger.error("Customers not found");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "allPaginated")
    public ResponseEntity<List<Customer>> getAllCustomersPaginated(@RequestBody int beginPage, int endPage) {
        Pageable page = PageRequest.of(beginPage, endPage);
        Page<Customer> customers = customerRepository.findAll(page);
        if (!customers.isEmpty()) {
            List<Customer> _customers = customers.stream().collect(Collectors.toList());
            logger.info("Get all books : {} ", customers.toString());
            return new ResponseEntity<>(_customers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "sendEmail")
    public ResponseEntity<Boolean> sendMailToCustomer(@RequestBody SimpleMail simpleMail) {
        Customer _customer = customerRepository.findCustomerById(simpleMail.getCustomerId());
        if (_customer == null) {
            logger.error("The selected Customer for sending email is not found in the database");
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } else if (_customer.getEmail().isEmpty()) {
            logger.error("No existing email for the selected Customer for sending email to");
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(simpleMail.MAIL_FROM);
        mail.setTo(_customer.getEmail());
        mail.setSentDate(new Date());
        mail.setSubject(simpleMail.getEmailSubject());
        mail.setText(simpleMail.getEmailContent());
        try {
            javaMailSender.send(mail);
        } catch (MailException mailException) {
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
