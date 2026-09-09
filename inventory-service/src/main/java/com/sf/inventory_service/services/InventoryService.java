package com.sf.inventory_service.services;

import com.sf.inventory_service.dto.InventoryResponse;
import com.sf.inventory_service.dto.StockUpdateRequest;
import com.sf.inventory_service.entities.Inventory;
import com.sf.inventory_service.exceptions.InventoryNotFoundException;
import com.sf.inventory_service.repo.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    // Native Constructor for Dependency Injection (Replaces @RequiredArgsConstructor)
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public InventoryResponse checkStock(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new InventoryNotFoundException("SKU Code " + skuCode + " not registered in system."));

        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .availableQuantity(inventory.getQuantity())
                .build();
    }

    @Transactional
    public Inventory addOrUpdateStock(StockUpdateRequest request) {
        Inventory inventory = inventoryRepository.findBySkuCode(request.getSkuCode())
                .orElse(Inventory.builder().skuCode(request.getSkuCode()).quantity(0).build());

        inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        return inventoryRepository.save(inventory);
    }
}