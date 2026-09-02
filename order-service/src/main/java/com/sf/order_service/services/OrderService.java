package com.sf.order_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.sf.order_service.dto.InventoryResponse;
import com.sf.order_service.dto.OrderRequest;
import com.sf.order_service.entities.Order;
import com.sf.order_service.exceptions.OutOfStockException;
import com.sf.order_service.repo.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Slf4j
public class OrderService {

	// Add this injection property inside your OrderService class block:
    private final  org.springframework.kafka.core.KafkaTemplate<String, String> kafkaTemplate;
    
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderService.class);
    @Transactional
    public String placeOrder(OrderRequest orderRequest) {
        log.info("[ORDER SERVICE] Initiating transaction sequence for item SKU: {}", orderRequest.getSkuCode());

        
        // Inter-Service Communication Block: Synchronously questioning inventory status
        InventoryResponse inventoryResponse = webClientBuilder.build().get()
                .uri("http://INVENTORY-SERVICE/api/inventory/" + orderRequest.getSkuCode())
                .retrieve()
                .bodyToMono(InventoryResponse.class) // Convert incoming JSON to clean Java DTO
                .block(); // Forces synchronous execution (waiting for response)

        // Logical Validation Evaluation
        if (inventoryResponse != null && inventoryResponse.isInStock() && inventoryResponse.getAvailableQuantity() >= orderRequest.getQuantity()) {
        	
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .skuCode(orderRequest.getSkuCode())
                    .price(orderRequest.getPrice())
                    .quantity(orderRequest.getQuantity())
                    .build();
         // Right inside your placeOrder() method, inside the success "if" block, add this call:
            kafkaTemplate.send("order-topic", order.getOrderNumber());
            log.info("[ORDER SERVICE] Asynchronous registration event stream transmitted to Kafka topic.");
            orderRepository.save(order);
            log.info("[ORDER SERVICE] Transaction success! Assigned reference token: {}", order.getOrderNumber());
            return "Order placed successfully. Reference number: " + order.getOrderNumber();
            
        } else {
            log.error("[ORDER SERVICE] Purchase failed. Insufficient stock capacity verified for SKU: {}", orderRequest.getSkuCode());
            throw new OutOfStockException("Requested item stock unavailable or quantity selection exceeds warehouse availability.");
        }
    }
 
}
