package com.project.webstore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.webstore.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	// used for finding a customer by customer id
	Customer findBy_id(String id);
}
