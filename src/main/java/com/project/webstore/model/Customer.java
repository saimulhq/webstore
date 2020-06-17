package com.project.webstore.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// model class for the collection customers
@Document(collection = "customers")
public class Customer {
	@Id
	private String _id; // represents the customer id
	@NotEmpty(message = "name cannot be empty or missing")
	private String name;
	@NotEmpty(message = "address cannot be empty or missing")
	private String address;
	@Email(message = "must be proper email address")
	@NotEmpty(message = "email cannot be empty or missing")
	private String email;
	@NotEmpty(message = "phone cannot be empty or missing")
	private String phone;

	public Customer(String _id, @NotEmpty(message = "name cannot be empty or missing") String name,
			@NotEmpty(message = "address cannot be empty or missing") String address,
			@Email(message = "must be proper email address") @NotEmpty(message = "email cannot be empty or missing") String email,
			@NotEmpty(message = "phone cannot be empty or missing") String phone) {
		super();
		this._id = _id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Customer [_id=" + _id + ", name=" + name + ", address=" + address + ", email=" + email + ", phone="
				+ phone + "]";
	}
}
