package com.sf.inventory_service.dto;

public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
    private Integer availableQuantity;

    public InventoryResponse() {}

    public InventoryResponse(String skuCode, boolean isInStock, Integer availableQuantity) {
        this.skuCode = skuCode;
        this.isInStock = isInStock;
        this.availableQuantity = availableQuantity;
    }

    // Manual Native Builder Pattern (Keeps your Service code unchanged)
    public static class InventoryResponseBuilder {
        private String skuCode;
        private boolean isInStock;
        private Integer availableQuantity;

        public InventoryResponseBuilder skuCode(String skuCode) { this.skuCode = skuCode; return this; }
        public InventoryResponseBuilder isInStock(boolean isInStock) { this.isInStock = isInStock; return this; }
        public InventoryResponseBuilder availableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; return this; }

        public InventoryResponse build() {
            return new InventoryResponse(skuCode, isInStock, availableQuantity);
        }
    }

    public static InventoryResponseBuilder builder() {
        return new InventoryResponseBuilder();
    }

    // Getters and Setters
    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public boolean isInStock() { return isInStock; }
    public void setInStock(boolean isInStock) { this.isInStock = isInStock; }

    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }
}