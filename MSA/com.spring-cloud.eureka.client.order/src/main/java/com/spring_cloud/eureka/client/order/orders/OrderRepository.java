package com.spring_cloud.eureka.client.order.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_cloud.eureka.client.order.core.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
