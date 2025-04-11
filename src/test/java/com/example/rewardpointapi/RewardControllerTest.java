package com.example.rewardpointapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rewardpointapi.controller.RewardController;
import com.example.rewardpointapi.dto.RewardDTO;
import com.example.rewardpointapi.dto.TransactionRequestDTO;
import com.example.rewardpointapi.service.RewardService;
import com.fasterxml.jackson.databind.ObjectMapper;

// to load only the web layer, specifically the RewardController.
@WebMvcTest(RewardController.class)
public class RewardControllerTest {

	// MockMvc allows us to send HTTP requests to the controller
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RewardService rewardService;

	// Used to convert Java objects to JSON (especially for POST requests with
	// body).
	@Autowired
	private ObjectMapper objectMapper;

	// Mocks the behavior of rewardService.getAllCustomerRewards() to return an
	// empty list.
	@Test
	void testGetRewards() throws Exception {
		Mockito.when(rewardService.getAllCustomerRewards()).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/rewards")).andExpect(status().isOk());
	}

	// Prepares a mock RewardDTO with sample data.
	// calling the GET /rewards/1 endpoint.
	@Test
	void testGetRewardsByCustomerId() throws Exception {
		RewardDTO dto = new RewardDTO();
		dto.setCustomerId(1L);
		dto.setCustomerName("Raj");
		Mockito.when(rewardService.calculateRewardsForCustomer(1L)).thenReturn(dto);

		mockMvc.perform(get("/rewards/1")).andExpect(status().isOk()).andExpect(jsonPath("$.customerId").value(1L))
				.andExpect(jsonPath("$.customerName").value("Raj"));
	}

	/*
	 * Sends a POST request to /rewards/add with the JSON body. Asserts: Response
	 * status is 201 Created. Response body is "Transaction saved successfully"
	 */
	@Test
	void testAddTransaction() throws Exception {
		TransactionRequestDTO requestDTO = new TransactionRequestDTO();
		requestDTO.setCustomerId(1L);
		requestDTO.setCustomerName("Raj");
		requestDTO.setAmount(150.0);
		requestDTO.setTransactionDate(LocalDateTime.now());

		mockMvc.perform(post("/rewards/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDTO))).andExpect(status().isCreated())
				.andExpect(content().string("Transaction saved successfully"));
	}
}
