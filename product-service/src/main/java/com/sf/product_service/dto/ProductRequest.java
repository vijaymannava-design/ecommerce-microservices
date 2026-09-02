package com.sf.product_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
