package com.sf.order_service.controllers;


import com.sf.order_service.dto.OrderRequest;
import com.sf.order_service.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        String confirmationMessage = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(confirmationMessage, HttpStatus.CREATED);
    }
}
