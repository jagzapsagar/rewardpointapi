package com.example.rewardpointapi.service;

import java.util.List;

import com.example.rewardpointapi.dto.RewardDTO;
import com.example.rewardpointapi.dto.TransactionRequestDTO;

public interface RewardService {

	public RewardDTO calculateRewardsForCustomer(Long customerId);

	public List<RewardDTO> getAllCustomerRewards();

	public void saveTransaction(TransactionRequestDTO request);

}
