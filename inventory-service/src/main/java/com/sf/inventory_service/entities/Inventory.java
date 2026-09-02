package com.sf.inventory_service.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String skuCode; // Matches the SKU Code from product-service
    
    @Column(nullable = false)
    private Integer quantity; // Total stock available
}
