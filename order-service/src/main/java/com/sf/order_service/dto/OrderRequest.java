package com.sf.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

public class OrderRequest {
    @NotBlank(message = "SKU Code cannot be blank")
    private String skuCode;

    @NotNull(message = "Price is mandatory")
    private BigDecimal price;

    @NotNull(message = "Quantity must be specified")
    @Min(value = 1, message = "Quantity must be at least 1 item")
    private Integer quantity;

	public OrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderRequest(@NotBlank(message = "SKU Code cannot be blank") String skuCode,
			@NotNull(message = "Price is mandatory") BigDecimal price,
			@NotNull(message = "Quantity must be specified") @Min(value = 1, message = "Quantity must be at least 1 item") Integer quantity) {
		super();
		this.skuCode = skuCode;
		this.price = price;
		this.quantity = quantity;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
    
}
