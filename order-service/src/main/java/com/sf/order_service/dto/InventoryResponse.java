package com.sf.order_service.dto;

import lombok.*;

public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
    private Integer availableQuantity;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public boolean isInStock() {
		return isInStock;
	}
	public void setInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}
	public Integer getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public InventoryResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InventoryResponse(String skuCode, boolean isInStock, Integer availableQuantity) {
		super();
		this.skuCode = skuCode;
		this.isInStock = isInStock;
		this.availableQuantity = availableQuantity;
	}
    
    
}
