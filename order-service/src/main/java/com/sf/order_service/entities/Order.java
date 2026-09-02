package com.sf.order_service.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderNumber; // Auto-generated UUID tracking token
    private String skuCode;     // Cross-service reference link
    private BigDecimal price;   // Purchased item price
    private Integer quantity;   // Total units ordered
}
