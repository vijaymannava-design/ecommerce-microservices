package com.sf.product_service.entities;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String skuCode; // Stock Keeping Unit (Link point for inventory-service)
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    private String category;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    private boolean isActive;
}
