package com.spring_cloud.eureka.client.product.products;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ProductSearchDto {
	private String name;
	private String description;
	private Double minPrice;
	private Double maxPrice;
	private Integer minQuantity;
	private Integer maxQuantity;
}
