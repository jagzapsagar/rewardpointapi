package com.example.rewardpointapi.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rewardpointapi.dto.RewardDTO;
import com.example.rewardpointapi.dto.TransactionRequestDTO;
import com.example.rewardpointapi.entity.Transaction;
import com.example.rewardpointapi.exception.NoTransactionDataFoundException;
import com.example.rewardpointapi.exception.ResourceNotFoundException;
import com.example.rewardpointapi.repository.TransactionRepository;

@Service
public class RewardService {

	@Autowired
	private TransactionRepository transactionRepository;

	public RewardDTO calculateRewardsForCustomer(Long customerId) {
		List<Transaction> customerTransactions = transactionRepository.findByCustomerId(customerId);

		if (customerTransactions.isEmpty()) {
			throw new ResourceNotFoundException("No transactions found for customer ID: " + customerId);
		}

		return buildRewardDTO(customerId, customerTransactions);
	}

	public List<RewardDTO> getAllCustomerRewards() {
		List<Transaction> transactions = transactionRepository.findAll();
		if (transactions.isEmpty()) {
			throw new NoTransactionDataFoundException("No transaction records found.");
		}
		Map<Long, List<Transaction>> groupedByCustomer = transactions.stream()
				.collect(Collectors.groupingBy(Transaction::getCustomerId));

		List<RewardDTO> responses = new ArrayList<>();

		for (Map.Entry<Long, List<Transaction>> entry : groupedByCustomer.entrySet()) {
			responses.add(buildRewardDTO(entry.getKey(), entry.getValue()));
		}
		return responses;
	}

	public void saveTransaction(TransactionRequestDTO request) {
		if (request.getAmount() == null || request.getAmount() <= 0) {
			throw new IllegalArgumentException("Transaction amount must be greater than 0");
		}

		Transaction txn = new Transaction();
		txn.setCustomerId(request.getCustomerId());
		txn.setCustomerName(request.getCustomerName());
		txn.setAmount(request.getAmount());
		txn.setTransactionDate(
				request.getTransactionDate() != null ? request.getTransactionDate() : LocalDateTime.now());

		transactionRepository.save(txn);
	}

	// Common logic
	private RewardDTO buildRewardDTO(Long customerId, List<Transaction> customerTransactions) {
		String customerName = customerTransactions.get(0).getCustomerName();
		Map<String, Integer> monthlyRewards = new HashMap<>();
		int totalPoints = 0;

		for (Transaction txn : customerTransactions) {
			int points = calculatePoints(txn.getAmount());
			YearMonth ym = YearMonth.from(txn.getTransactionDate());
			String month = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "-" + ym.getYear();
			monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
			totalPoints += points;
		}

		RewardDTO response = new RewardDTO();
		response.setCustomerId(customerId);
		response.setCustomerName(customerName);
		response.setMonthlyRewards(monthlyRewards);
		response.setTotalPoints(totalPoints);

		return response;
	}

	public int calculatePoints(Double amount) {
		int points = 0;
		if (amount > 100) {
			points += (int) (2 * (amount - 100));
			points += 50;
		} else if (amount > 50) {
			points += (int) (amount - 50);
		}
		return points;
	}

}
