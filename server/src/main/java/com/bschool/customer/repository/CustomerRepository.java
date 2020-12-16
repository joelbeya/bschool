package com.bschool.customer.repository;

import com.bschool.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerByEmailContainingIgnoreCase(String email);

    Customer findCustomerById(int id);

    List<Customer> findCustomerByLastNameContainingIgnoreCase(String lastName);

    List<Customer> findCustomerByFirstNameContainingIgnoreCase(String firstName);

}
