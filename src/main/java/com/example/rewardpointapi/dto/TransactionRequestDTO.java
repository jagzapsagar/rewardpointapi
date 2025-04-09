package com.example.rewardpointapi.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionRequestDTO {

	@NotNull(message = "Customer ID is required")
	@Positive(message = "Customer ID must be a positive number")
	private Long customerId;
	@NotBlank(message = "Customer name is required")
	private String customerName;
	private Double amount;
	private LocalDateTime transactionDate;

	public TransactionRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionRequestDTO(Long customerId, String customerName, Double amount, LocalDateTime transactionDate) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "TransactionRequestDTO [customerId=" + customerId + ", customerName=" + customerName + ", amount="
				+ amount + ", transactionDate=" + transactionDate + "]";
	}

}
