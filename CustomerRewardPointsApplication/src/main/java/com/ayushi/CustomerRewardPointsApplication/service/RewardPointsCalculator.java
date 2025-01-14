package com.ayushi.CustomerRewardPointsApplication.service;

import com.ayushi.CustomerRewardPointsApplication.model.Transaction;
import com.ayushi.CustomerRewardPointsApplication.repository.TransactionRepository;
import com.ayushi.CustomerRewardPointsApplication.CustomException.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardPointsCalculator {

    @Autowired
    public TransactionRepository transactionRepository;

    // Calculate points for a single transaction
    public int calculatePoints(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

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

    // Calculate monthly points for a customer
    public int calculateMonthlyPoints(String customerId, String month) {
        if (customerId == null || month == null) {
            throw new IllegalArgumentException("Customer ID and Month cannot be null");
        }

        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByCustomerIdAndMonth(customerId, month);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while fetching transactions", e);
        }

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found for customer: " + customerId + " in month: " + month);
        }

        return transactions.stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }

    // Calculate total points for a customer
    public int calculateTotalPoints(String customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }

        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findByCustomerId(customerId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while fetching transactions", e);
        }

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found for customer: " + customerId);
        }

        return transactions.stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }
}
