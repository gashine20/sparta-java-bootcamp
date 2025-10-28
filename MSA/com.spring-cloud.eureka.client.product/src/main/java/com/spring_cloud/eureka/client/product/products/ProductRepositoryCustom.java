package com.spring_cloud.eureka.client.product.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring_cloud.eureka.client.product.core.Product;

public interface ProductRepositoryCustom {
	Page<ProductResponseDto> searchProducts(ProductSearchDto searchDto,  Pageable pageable);
}
