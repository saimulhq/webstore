package com.project.webstore.controller;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.webstore.errorhandler.ErrorResponseBodyGenerator;
import com.project.webstore.model.Customer;
import com.project.webstore.model.Order;
import com.project.webstore.repository.CustomerRepository;
import com.project.webstore.repository.OrderRepository;

@RestController
public class WebstoreController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	// error message generator
	ErrorResponseBodyGenerator errorResponseBodyGenerator = new ErrorResponseBodyGenerator();

	// create a customer
	@PostMapping(value = "/customers/")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer) {
		Customer newCustomer = customerRepository.save(customer);

		if (newCustomer != null) {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(newCustomer.get_id()).toUri();

			return ResponseEntity.created(uri).body(newCustomer);
		} else {
			String errorMessage = "customer could not be created";

			List<String> errors = new ArrayList<String>();

			errors.add(errorMessage);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.UNPROCESSABLE_ENTITY, errors);

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
		}
	}

	// get customer information using customer id
	@GetMapping(value = "/customers/{id}")
	public ResponseEntity<Object> getCustomerById(@PathVariable(name = "id") String id) {
		Customer customer = customerRepository.findBy_id(id);

		if (customer == null) {
			String errorMessage = "customer with id [" + id + "] not found";

			List<String> errors = new ArrayList<String>();

			errors.add(errorMessage);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, errors);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		} else {
			return ResponseEntity.ok(customer);
		}
	}

	// update customer information using customer id
	@PutMapping(value = "/customers/{id}")
	public ResponseEntity<Object> modifyCustomerById(@PathVariable(name = "id") String id,
			@Valid @RequestBody Customer updatedCustomer) {
		Customer customer = customerRepository.findBy_id(id);

		if (customer == null) {
			String errorMessage = "customer with id [" + id + "] not found";

			List<String> errors = new ArrayList<String>();

			errors.add(errorMessage);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, errors);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		} else {
			updatedCustomer.set_id(id);
			customerRepository.save(updatedCustomer);
			return ResponseEntity.ok(updatedCustomer);
		}
	}

	// get the list of all customers
	@GetMapping(value = "/customers/")
	public ResponseEntity<Object> getAllCustomers() {
		List<Customer> allCustomers = customerRepository.findAll();

		if (!allCustomers.isEmpty()) {
			return ResponseEntity.ok(allCustomers);
		} else {
			String message = "no customer data in database";

			List<String> messages = new ArrayList<String>();

			messages.add(message);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, messages);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		}
	}

	// create an order
	@PostMapping(value = "/orders/")
	public ResponseEntity<Object> createOrder(@Valid @RequestBody Order newOrder) {

		Customer customer = customerRepository.findBy_id(newOrder.getCustomerId());

		if (customer == null) {
			String errorMessage = "customer with id [" + newOrder.getCustomerId() + "] not found";

			List<String> errors = new ArrayList<String>();

			errors.add(errorMessage);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, errors);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		} else {
			Instant orderTime = Instant.now();
			newOrder.setOrderTime(orderTime);
			Order order = orderRepository.save(newOrder);

			if (order != null) {
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.get_id())
						.toUri();

				return ResponseEntity.created(uri).body(order);
			} else {
				String errorMessage = "order could not be created";

				List<String> errors = new ArrayList<String>();

				errors.add(errorMessage);

				Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
						"" + HttpStatus.UNPROCESSABLE_ENTITY, errors);

				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
			}
		}
	}

	// delete an order
	@DeleteMapping(value = "/orders/{orderId}")
	public ResponseEntity<Object> deleteByOrderId(@PathVariable String orderId) {
		Order order = orderRepository.findBy_id(orderId);

		if (order == null) {
			String errorMessage = "order with id [" + orderId + "] not found";

			List<String> errors = new ArrayList<String>();

			errors.add(errorMessage);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, errors);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		} else {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}
	}

	// get the list of orders by customer id
	@GetMapping(value = "/orders/{customerId}")
	public ResponseEntity<Object> getAllOrdersByCustomerId(@PathVariable String customerId) {
		List<Order> allOrdersFromCustomer = orderRepository.findByCustomerId(customerId);

		if (!allOrdersFromCustomer.isEmpty()) {
			return ResponseEntity.ok(allOrdersFromCustomer);
		} else {
			String errorMessage = "customer with id [" + customerId + "] not found";

			List<String> errors = new ArrayList<String>();

			errors.add(errorMessage);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, errors);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		}
	}

	// get the list of all orders
	@GetMapping(value = "/orders/")
	public ResponseEntity<Object> getAllOrders() {
		List<Order> allOrders = orderRepository.findAll();

		if (!allOrders.isEmpty()) {
			return ResponseEntity.ok(allOrders);
		} else {
			String message = "no order data in the database";

			List<String> messages = new ArrayList<String>();

			messages.add(message);

			Map<String, Object> body = errorResponseBodyGenerator.generateErrorResponseBody(Instant.now(),
					"" + HttpStatus.NOT_FOUND, messages);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		}
	}
}
