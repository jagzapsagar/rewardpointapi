package com.example.rewardpointapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.rewardpointapi.dto.RewardDTO;
import com.example.rewardpointapi.dto.TransactionRequestDTO;
import com.example.rewardpointapi.entity.Transaction;
import com.example.rewardpointapi.exception.NoTransactionDataFoundException;
import com.example.rewardpointapi.exception.ResourceNotFoundException;
import com.example.rewardpointapi.repository.TransactionRepository;
import com.example.rewardpointapi.service.RewardServiceIMPL;

public class RewardServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private RewardServiceIMPL rewardService;

	public RewardServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Scenario: - Given: A customer ID that does not exist in the database. - When:
	 * The method is called to calculate rewards for this customer. - Then: It
	 * should throw a ResourceNotFoundException.
	 */
	@Test
	void testGetCustomerRewards_InvalidCustomerId_ThrowsException() {
		Long invalidCustomerId = 999L;
		when(transactionRepository.findByCustomerId(invalidCustomerId)).thenReturn(Collections.emptyList());

		assertThrows(ResourceNotFoundException.class, () -> {
			rewardService.calculateRewardsForCustomer(invalidCustomerId);
		});
	}

	/**
	 * Scenario: - Given: The transaction repository returns an empty list. - When:
	 * The method is called to fetch rewards for all customers. - Then: It should
	 * throw a NoTransactionDataFoundException.
	 */
	@Test
	void testGetAllCustomerRewards_NoTransactionsFound() {
		when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

		assertThrows(NoTransactionDataFoundException.class, () -> {
			rewardService.getAllCustomerRewards();
		});
	}

	@Test
	void testPointsExactly100() {
		assertEquals(50, rewardService.calculatePoints(100.0)); // 50 (50 to 100)
	}

	@Test
	public void testGetCustomerRewards_withValidData() {
		Transaction txn1 = new Transaction(1001L, 1L, "John", 120.0, LocalDateTime.now());
		Transaction txn2 = new Transaction(1002L, 1L, "John", 70.0, LocalDateTime.now());
		when(transactionRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(txn1, txn2));

		RewardDTO response = rewardService.calculateRewardsForCustomer(1L);

		assertEquals(1L, response.getCustomerId());
		assertEquals("John", response.getCustomerName());
		assertNotNull(response.getMonthlyRewards());
		assertTrue(response.getTotalPoints() > 0);
	}

	@Test
	void testSaveTransaction_WithInvalidAmount_ShouldThrowException() {
		// Prepare input with invalid amount (e.g., 0 or null)
		TransactionRequestDTO invalidRequest = new TransactionRequestDTO();
		invalidRequest.setCustomerId(101L);
		invalidRequest.setCustomerName("User");
		invalidRequest.setAmount(0.0); // or set null to test null case
		invalidRequest.setTransactionDate(LocalDateTime.now());

		// Expect IllegalArgumentException when calling the service
		assertThrows(IllegalArgumentException.class, () -> {
			rewardService.saveTransaction(invalidRequest);
		});
	}

}
