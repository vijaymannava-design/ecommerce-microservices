package com.sf.product_service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sf.product_service.dto.ProductRequest;
import com.sf.product_service.entities.Product;
import com.sf.product_service.exceptions.ProductNotFoundException;
import com.sf.product_service.repo.ProductRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
//@Slf4j
@Data
public class ProductService {

    private final ProductRepository productRepository;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProductService.class);
    public Product createProduct(ProductRequest request) {
        log.info("[PRODUCT SERVICE] Executing product creation for SKU: {}", request.getSkuCode());
        
        Product product = Product.builder()
                .skuCode(request.getSkuCode())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .isActive(true)
                .build();

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        log.info("[PRODUCT SERVICE] Request triggered to fetch all catalog items.");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        log.info("[PRODUCT SERVICE] Locating details for Product ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product profile matching internal code " + id + " does not exist."));
    }

    public Product updateProduct(Long id, ProductRequest request) {
        log.info("[PRODUCT SERVICE] Commencing data rewrite sequence for Product ID: {}", id);
        Product existingProduct = getProductById(id);

        existingProduct.setSkuCode(request.getSkuCode());
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setPrice(request.getPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        log.warn("[PRODUCT SERVICE] Soft-purging entity visibility for product mapping reference ID: {}", id);
        Product existingProduct = getProductById(id);
        existingProduct.setActive(false); // Enterprise standard soft-delete
        productRepository.save(existingProduct);
    }
}