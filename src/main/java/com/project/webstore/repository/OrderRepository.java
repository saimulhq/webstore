package com.project.webstore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.webstore.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	// used for finding the list of orders by customer id
	List<Order> findByCustomerId(String customerId);

	// used for finding an order by order id
	Order findBy_id(String id);
}
