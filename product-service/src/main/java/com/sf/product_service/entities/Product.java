package com.sf.product_service.entities;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String skuCode; 
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    private String category;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    private boolean isActive;

    // Constructors
    public Product() {}

    public Product(Long id, String skuCode, String name, String description, String category, BigDecimal price, boolean isActive) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
}