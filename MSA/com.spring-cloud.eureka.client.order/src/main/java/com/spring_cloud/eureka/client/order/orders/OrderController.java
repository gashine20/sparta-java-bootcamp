package com.spring_cloud.eureka.client.order.orders;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto,
		@RequestHeader(value = "X-User-Id", required = true) String userId,
		@RequestHeader(value = "X-Role", required = true) String role) {

		return orderService.createOrder(orderRequestDto, userId);
	}

	@GetMapping
	public Page<OrderResponseDto> getOrders(
		OrderSearchDto searchDto,
		Pageable pageable,
		@RequestHeader(value = "X-User-Id") String userId,
		@RequestHeader(value = "X-Role", required = true) String role
	) {
		if(!"MANAGER".equals(role)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
		}
		return orderService.getOrders(searchDto, pageable,role, userId);
	}

	@GetMapping("/{orderId}")
	public OrderResponseDto getOrderById(
		@PathVariable Long orderId
	) {
		return orderService.getOrderById(orderId);
	}

	@PutMapping("/{orderId}")
	public OrderResponseDto updateOrder(
		@PathVariable Long orderId,
		@RequestBody OrderRequestDto requestDto,
		String userId
	) {
		return orderService.updateOrder(orderId, requestDto, userId);
	}

	@DeleteMapping("/{orderId}")
	public void deleteORder(
		@PathVariable Long orderId,
		String userId
	) {
		orderService.deleteOrder(orderId, userId);
	}
}
