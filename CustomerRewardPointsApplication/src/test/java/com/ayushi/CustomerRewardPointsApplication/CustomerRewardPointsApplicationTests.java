package com.ayushi.CustomerRewardPointsApplication;

import com.ayushi.CustomerRewardPointsApplication.CustomException.TransactionNotFoundException;
import com.ayushi.CustomerRewardPointsApplication.service.RewardPointsCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.ayushi.CustomerRewardPointsApplication.model.Transaction;
import com.ayushi.CustomerRewardPointsApplication.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerRewardPointsApplicationTests {

		@Mock
		private TransactionRepository transactionRepository;

		@InjectMocks
		private RewardPointsCalculator rewardPointsCalculator;

		@BeforeEach
		public void setup() {
			MockitoAnnotations.openMocks(this);
		}

	@Test
		public void testCalculatePoints() {

			Transaction transaction = new Transaction("12345", 150.0, "January");

			int points = rewardPointsCalculator.calculatePoints(transaction);

			assertEquals(150, points);
		}

		@Test
		public void testCalculatePoints_AmountAbove50() {
			Transaction transaction = new Transaction("12345", 70.0, "February");

			int points = rewardPointsCalculator.calculatePoints(transaction);

			assertEquals(20, points);
		}

		@Test
		public void testCalculatePoints_AmountBelow50() {

			Transaction transaction = new Transaction("12345", 40.0, "March");

			int points = rewardPointsCalculator.calculatePoints(transaction);

			assertEquals(0, points);
		}

		@Test
		public void testCalculateMonthlyPoints() {
			// Given a list of transactions for customer "12345" in January
			List<Transaction> transactions = Arrays.asList(
					new Transaction("12345", 150.0, "January"),
					new Transaction("12345", 75.0, "January")
			);

			when(transactionRepository.findByCustomerIdAndMonth("12345", "January")).thenReturn(transactions);

			int totalPoints = rewardPointsCalculator.calculateMonthlyPoints("12345", "January");

			assertEquals(175, totalPoints);
		}


		@Test
		public void testCalculateTotalPoints() {

			List<Transaction> transactions = Arrays.asList(
					new Transaction("12345", 150.0, "January"),
					new Transaction("12345", 75.0, "February"),
					new Transaction("12345", 200.0, "March")
			);

			when(transactionRepository.findByCustomerId("12345")).thenReturn(transactions);

			int totalPoints = rewardPointsCalculator.calculateTotalPoints("12345");

			assertEquals(425, totalPoints);
		}

	@Test
	public void testCalculateTotalPoints_NullCustomerId() {
		assertThrows(IllegalArgumentException.class, () -> rewardPointsCalculator.calculateTotalPoints(null), "Customer ID cannot be null");
	}

	@Test
	public void testCalculateTotalPoints_NoTransactionsFound() {
		when(transactionRepository.findByCustomerId("C001")).thenReturn(List.of());

		assertThrows(TransactionNotFoundException.class, () -> rewardPointsCalculator.calculateTotalPoints("C001"), "No transactions found for customer: C001");
	}

	@Test
	public void testCalculateMonthlyPoints_NoTransactionsFound() {
		when(transactionRepository.findByCustomerIdAndMonth("C001", "January")).thenReturn(List.of());

		assertThrows(TransactionNotFoundException.class, () -> rewardPointsCalculator.calculateMonthlyPoints("C001", "January"), "No transactions found for customer: C001 in month: January");
	}

	@Test
	public void testCalculateMonthlyPoints_NullCustomerId() {
		assertThrows(IllegalArgumentException.class, () -> rewardPointsCalculator.calculateMonthlyPoints(null, "January"), "Customer ID and Month cannot be null");
	}

	@Test
	public void testCalculateMonthlyPoints_NullMonth() {
		assertThrows(IllegalArgumentException.class, () -> rewardPointsCalculator.calculateMonthlyPoints("C001", null), "Customer ID and Month cannot be null");
	}

}



