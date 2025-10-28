package com.spring_cloud.eureka.client.product.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public ProductResponseDto createProduct(
		@RequestBody ProductRequestDto productRequestDto,
		@RequestHeader(value = "X-User-Id", required = true) String userId,
		@RequestHeader(value = "X-Role", required = true) String role) {

		if (!"MANAGER".equals(role)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
		}

		return productService.createProduct(productRequestDto, userId);
	}

	@GetMapping
	public Page<ProductResponseDto> getProducts(
		ProductSearchDto searchDto,
		Pageable pageable) {
		return productService.getProducts(searchDto, pageable);
	}

	@GetMapping("/{productId}")
	public ProductResponseDto getProductById(@PathVariable Long productId) {
		return productService.getProductById(productId);
	}

	@PutMapping("/{productId}")
	public ProductResponseDto updateProduct(
		Long productId,
		ProductRequestDto requestDto,
		String userId
	) {
		return productService.updateProduct(productId, requestDto, userId);
	}

	@DeleteMapping("/{productId}")
	public void deleteProduct(
		@PathVariable Long productId,
		@RequestParam String deletedBy
	) {
		productService.deleteProduct(productId, deletedBy);
	}

	@GetMapping("/{id}/reduceQuantity")
	public void reduceProductQuantity(@PathVariable Long productId, @RequestParam int quantity) {
		productService.reduceProductQuantity(productId, quantity);
	}
}
