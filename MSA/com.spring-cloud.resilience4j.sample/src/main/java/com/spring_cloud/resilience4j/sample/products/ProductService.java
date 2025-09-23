package com.spring_cloud.resilience4j.sample.products;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    @CircuitBreaker(name="productService", fallbackMethod = "fallbackGetProductDetails")
    public Product getProductDetails(String productId){
        if("111".equals(productId)){
            throw new RuntimeException("Empty response body");
        }
        return new Product(productId, "Sample Product");
    }

    public Product fallbackGetProductDetails(String productId, Throwable t){
        return new Product(productId, "Fallback Product");
    }
}
