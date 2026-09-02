package com.sf.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotBlank(message = "SKU Code cannot be blank")
    private String skuCode;

    @NotNull(message = "Price is mandatory")
    private BigDecimal price;

    @NotNull(message = "Quantity must be specified")
    @Min(value = 1, message = "Quantity must be at least 1 item")
    private Integer quantity;
}
