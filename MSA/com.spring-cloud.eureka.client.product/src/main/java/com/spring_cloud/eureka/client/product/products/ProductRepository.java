package com.spring_cloud.eureka.client.product.products;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_cloud.eureka.client.product.core.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
