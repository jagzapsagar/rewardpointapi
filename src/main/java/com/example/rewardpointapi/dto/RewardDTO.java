package com.example.rewardpointapi.dto;

import java.util.Map;

public class RewardDTO {
	private Long customerId;
	private String customerName;
	private Map<String, Integer> monthlyRewards;
	private Integer totalPoints;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Map<String, Integer> getMonthlyRewards() {
		return monthlyRewards;
	}

	public void setMonthlyRewards(Map<String, Integer> monthlyRewards) {
		this.monthlyRewards = monthlyRewards;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

}
