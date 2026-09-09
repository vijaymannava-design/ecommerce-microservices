package com.sf.inventory_service.entities;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String skuCode;
    
    @Column(nullable = false)
    private Integer quantity;

    // Standard Constructors
    public Inventory() {}

    public Inventory(Long id, String skuCode, Integer quantity) {
        this.id = id;
        this.skuCode = skuCode;
        this.quantity = quantity;
    }

    // Manual Native Builder Pattern
    public static class InventoryBuilder {
        private String skuCode;
        private Integer quantity;

        public InventoryBuilder skuCode(String skuCode) { this.skuCode = skuCode; return this; }
        public InventoryBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }

        public Inventory build() {
            return new Inventory(null, skuCode, quantity);
        }
    }

    public static InventoryBuilder builder() {
        return new InventoryBuilder();
    }

    // Explicit Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}