package com.example.rewardpointapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rewardpointapi.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	//Optional<Transaction> findByCustomerId(String customerId);
	List<Transaction> findByCustomerId(Long customerId);

}
