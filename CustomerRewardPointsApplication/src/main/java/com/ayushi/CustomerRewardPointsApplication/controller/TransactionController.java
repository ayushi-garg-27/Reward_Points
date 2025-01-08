package com.ayushi.CustomerRewardPointsApplication.controller;

import com.ayushi.CustomerRewardPointsApplication.model.Transaction;
import com.ayushi.CustomerRewardPointsApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}

