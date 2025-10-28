package com.spring_cloud.eureka.client.order.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {
	@GetMapping("/products/{productId}")
	ProductResponseDto getProduct(@PathVariable("productId") Long id);

	@GetMapping("/products/{productId}/reduceQuantity")
	void reduceProductQuantity(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity);
}
