package com.ayushi.CustomerRewardPointsApplication.service;

import com.ayushi.CustomerRewardPointsApplication.model.Transaction;
import com.ayushi.CustomerRewardPointsApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardPointsCalculator {

    @Autowired
    private TransactionRepository transactionRepository;

    public int calculatePoints(Transaction transaction) {
        double amount = transaction.getAmount();
        int points = 0;

        if (amount > 100) {
            points += 2 * (amount - 100);
            amount = 100;
        }
        if (amount > 50) {
            points += 1 * (amount - 50);
        }

        return points;
    }

    public int calculateMonthlyPoints(String customerId, String month) {
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndMonth(customerId, month);
        return transactions.stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }

    public int calculateTotalPoints(String customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        return transactions.stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }
}

