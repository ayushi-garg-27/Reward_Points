package com.ayushi.CustomerRewardPointsApplication.repository;
import com.ayushi.CustomerRewardPointsApplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(String customerId);
}

