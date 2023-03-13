package com.pluralsight.globomantics.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String productType;
	
	@Column(nullable = false)
	private Integer  stockQuantity;

	public static final int STOCK_QUANTITY = 1000;

	public Products() {

	}

	public Products(String productType) {
		this.productType = productType;
		this.stockQuantity = STOCK_QUANTITY;
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
		return this.stockQuantity;
	}

	public void setQuantity(Integer quantity) {
		this.stockQuantity = quantity;
	}


	@Override
	public String toString() {
		return "Products [id=" + id + ", productType=" + productType + ", quantity=" + stockQuantity+ "]";
	}

}

