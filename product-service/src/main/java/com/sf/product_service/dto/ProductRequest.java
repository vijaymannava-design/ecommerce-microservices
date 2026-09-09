package com.sf.product_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

public class ProductRequest {
    @NotBlank(message = "SKU Code cannot be empty")
    private String skuCode;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private String description;
    
    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductRequest(@NotBlank(message = "SKU Code cannot be empty") String skuCode,
			@NotBlank(message = "Product name cannot be empty") String name, String description,
			@NotBlank(message = "Category cannot be empty") String category,
			@NotNull(message = "Price is mandatory") @Positive(message = "Price must be greater than zero") BigDecimal price) {
		super();
		this.skuCode = skuCode;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
	}
    
}
