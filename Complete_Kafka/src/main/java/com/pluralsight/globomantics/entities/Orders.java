package com.pluralsight.globomantics.entities;

import java.time.Instant;

import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String productType;

	@Column(nullable = false)
	private Integer  quantity;

	@Column(nullable = false)
	private Instant timestamp;

	public Orders() {

	}

	public Orders(JsonObject json) {
		this.productType = json.getString("productType");
		this.quantity = json.getJsonNumber("quantity").intValue();
		this.timestamp = Instant.ofEpochMilli(json.getJsonNumber("timestamp").longValue());
	}

	public Orders(String productType, Integer quantity, Instant timestamp) {
		super();
		this.productType = productType;
		this.quantity = quantity;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "OrderHistory [id=" + id + ", productType=" + productType + ", quantity=" + quantity + ", timestamp=" + timestamp
				+ "]";
	}

}
