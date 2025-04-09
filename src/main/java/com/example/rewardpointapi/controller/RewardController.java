package com.example.rewardpointapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rewardpointapi.dto.RewardDTO;
import com.example.rewardpointapi.dto.TransactionRequestDTO;
import com.example.rewardpointapi.service.RewardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rewards")
public class RewardController {
	@Autowired
	private RewardService rewardService;

	@GetMapping
	public List<RewardDTO> getRewards() {
		return rewardService.getAllCustomerRewards();
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<RewardDTO> getRewardsByCustomerId(@PathVariable Long customerId) {
		RewardDTO rewardDTO = rewardService.calculateRewardsForCustomer(customerId);
		return ResponseEntity.ok(rewardDTO);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addTransaction(@Valid @RequestBody TransactionRequestDTO request) {
		rewardService.saveTransaction(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("Transaction saved successfully");
	}

}
