package com.sf.inventory_service.controllers;

import com.sf.inventory_service.dto.InventoryResponse;
import com.sf.inventory_service.dto.StockUpdateRequest;
import com.sf.inventory_service.entities.Inventory;
import com.sf.inventory_service.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")

public class InventoryController {

    private final InventoryService inventoryService;

    
    public InventoryController(InventoryService inventoryService) {
		super();
		this.inventoryService = inventoryService;
	}

	// Endpoint for order-service to call synchronously via WebClient
    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryResponse> checkStock(@PathVariable String skuCode) {
        return ResponseEntity.ok(inventoryService.checkStock(skuCode));
    }

    // Endpoint to stock up items manually via Postman
    @PostMapping
    public ResponseEntity<Inventory> addStock(@Valid @RequestBody StockUpdateRequest request) {
        return new ResponseEntity<>(inventoryService.addOrUpdateStock(request), HttpStatus.CREATED);
    }
}
