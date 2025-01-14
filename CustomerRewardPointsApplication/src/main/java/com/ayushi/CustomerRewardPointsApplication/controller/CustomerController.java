package com.ayushi.CustomerRewardPointsApplication.controller;

import com.ayushi.CustomerRewardPointsApplication.model.Customer;
import com.ayushi.CustomerRewardPointsApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}

