package com.product.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class Product { 
	private int productId;
	@NotBlank(message = "Product name is mandatory")
	private String productName; 
	private String category; 
	@Min(value = 0, message = "Stock cannot be negative") 
	private int stock;
	@Positive(message = "Price should be greater than zero")
	private double price; }
