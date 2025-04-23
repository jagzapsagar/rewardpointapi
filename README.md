# Spring Boot Project with PostgrSql Database

This is a Spring Boot-based RESTful application that manages customer 
transactions and computes reward points based on business rules, 
simulating a real-world customer loyalty program.

---

##  Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL Database
- Maven
- Postman

---

### Objective

The application calculates **reward points** for customers based on the amount they spend in transactions. The rules are:

- **No rewards** for spending ≤ ₹50
- **1 point** for every ₹1 spent over ₹50 up to ₹100
- **2 points** for every ₹1 spent over ₹100

 API Endpoints
1. Add transactions
	URL: POST /rewards/add
	Description: Adds a new transaction for a customer.
	{
 	 "customerId": 101,
 	 "customerName": "Amit",
 	 "amount": 120.50,
  	 "transactionDate": "2025-04-08T10:30:00"
	 }
	 Response: 201 Created – Transaction saved successfully.
	 
2.  Get All Customer Rewards
	URL: GET /rewards
	Description: Returns reward points summary for all customers.
	Response Example:
	[
 	 {
    "customerId": 101,
    "customerName": "Amit",
    "monthlyRewards": {
      "April-2025": 90
    },
    "totalPoints": 90
 	 },
 	 {
    "customerId": 102,
    "customerName": "Ravi",
    "monthlyRewards": {
      "April-2025": 40
    },
    "totalPoints": 40
 	 }
	]
	
3.  Get Rewards by Customer ID
	URL: GET /rewards/{customerId}
	Description: Fetches reward points details for a specific customer.
	Path Variable: customerId — ID of the customer
	Response Example:
	{
   "customerId": 101,
   "customerName": "Amit",
   "monthlyRewards": {
    "April-2025": 90
   },
   "totalPoints": 90
    }


## Getting Started

## 1. Clone the Repository

git clone https://github.com/jagzapsagar/rewardpointapi/tree/main

## Project Structure

```text
reward-points-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── rewardpointapi/
│   │   │               ├── controller/
│   │   │               │   └── RewardController.java
│   │   │               ├── dto/
│   │   │               │   ├── RewardDTO.java
│   │   │               │   └── TransactionRequestDTO.java
│   │   │               ├── entity/
│   │   │               │   └── Transaction.java
│   │   │               ├── exception/
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── NoTransactionDataFoundException.java
│   │   │               │   └── ResourceNotFoundException.java
│   │   │               ├── repository/
│   │   │               │   └── TransactionRepository.java
│   │   │               ├── service/
│   │   │               │   ├── RewardService.java
│   │   │               │   └── RewardServiceImpl.java
│   │   │               └── RewardPointsApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── rewardpointapi/
│                       ├── RewardControllerTest.java
│                       ├── RewardServiceTest.java
│                       └── RewardPointsApiApplicationTests.java
├── .gitignore
├── pom.xml
└── README.md
```


## Database Table Structure

### transactions Table

| Column Name       | Data Type   | Description                                 |
|-------------------|-------------|---------------------------------------------|
| `id`              | BIGSERIAL   | Primary key, auto-generated ID              |
| `customer_id`	    | BIGINT      | Unique ID of the customer                   |
| `customer_name`   | VARCHAR     | Name of the customer                        |
| `amount`          | DECIMAL     | Transaction amount                          |
| `transaction_date`| TIMESTAMP   | Date and time when the transaction occurred |

### Sample DDL (PostgreSQL)

```sql
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL
);
```

	
