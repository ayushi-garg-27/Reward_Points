package com.ayushi.CustomerRewardPointsApplication.repository;

import com.ayushi.CustomerRewardPointsApplication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(String customerId);
    List<Transaction> findByCustomerIdAndMonth(String customerId, String month);
}

