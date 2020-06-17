package com.project.webstore.model;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//model class for the collection orders
@Document(collection = "orders")
public class Order {
	@Id
	private String _id; // represents the order id
	@NotEmpty(message = "customerId cannot be empty or missing")
	private String customerId;
	@NotEmpty(message = "bookTitle cannot be empty or missing")
	private String bookTitle;
	@NotEmpty(message = "bookType cannot be empty or missing")
	private String bookType;
	@NotEmpty(message = "bookAuthor cannot be empty or missing")
	private String bookAuthor;
	@NotEmpty(message = "bookDescription cannot be empty or missing")
	private String bookDescription;
	private Instant orderTime;

	public Order(String _id, @NotEmpty(message = "customerId cannot be empty or missing") String customerId,
			@NotEmpty(message = "bookTitle cannot be empty or missing") String bookTitle,
			@NotEmpty(message = "bookType cannot be empty or missing") String bookType,
			@NotEmpty(message = "bookAuthor cannot be empty or missing") String bookAuthor,
			@NotEmpty(message = "bookDescription cannot be empty or missing") String bookDescription,
			Instant orderTime) {
		super();
		this._id = _id;
		this.customerId = customerId;
		this.bookTitle = bookTitle;
		this.bookType = bookType;
		this.bookAuthor = bookAuthor;
		this.bookDescription = bookDescription;
		this.orderTime = orderTime;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public Instant getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Instant orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "Order [_id=" + _id + ", customerId=" + customerId + ", bookTitle=" + bookTitle + ", bookType="
				+ bookType + ", bookAuthor=" + bookAuthor + ", bookDescription=" + bookDescription + ", orderTime="
				+ orderTime + "]";
	}
}
