package com.spring_cloud.eureka.client.order.orders;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.spring_cloud.eureka.client.order.core.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {
	private OrderStatus status;
	private List<Long> orderItemIds;
	private String sortBy;
	private Pageable pageable;
}
