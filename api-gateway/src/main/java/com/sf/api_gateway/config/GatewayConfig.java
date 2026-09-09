package com.sf.api_gateway.config;

import com.sf.api_gateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter authFilter) {
        return builder.routes()
                // 1. PUBLIC AUTH SERVICE ROUTE
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))

                // 2. SECURED PRODUCT SERVICE ROUTE (Explicitly wraps via your manual filter!)
                .route("product-service", r -> r.path("/api/products/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://product-service"))

                // 3. SECURED ORDER SERVICE ROUTE (Explicitly wraps via your manual filter!)
                .route("order-service", r -> r.path("/api/orders/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://order-service"))

                // 4. INTERNAL INVENTORY SERVICE ROUTE
                .route("inventory-service", r -> r.path("/api/inventory/**")
                        .uri("lb://inventory-service"))
                .build();
    }
}