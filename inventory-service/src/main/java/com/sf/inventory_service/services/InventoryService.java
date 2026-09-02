package com.sf.inventory_service.services;

import com.sf.inventory_service.dto.InventoryResponse;
import com.sf.inventory_service.dto.StockUpdateRequest;
import com.sf.inventory_service.entities.Inventory;
import com.sf.inventory_service.exceptions.InventoryNotFoundException;
import com.sf.inventory_service.repo.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InventoryService.class);

    @Transactional(readOnly = true)
    public InventoryResponse checkStock(String skuCode) {
        log.info("[INVENTORY SERVICE] Checking stock availability for SKU: {}", skuCode);
        
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
        log.info("[INVENTORY SERVICE] Adjusting inventory parameters for SKU: {}", request.getSkuCode());
        
        Inventory inventory = inventoryRepository.findBySkuCode(request.getSkuCode())
                .orElse(Inventory.builder().skuCode(request.getSkuCode()).quantity(0).build());

        inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        return inventoryRepository.save(inventory);
    }
}
