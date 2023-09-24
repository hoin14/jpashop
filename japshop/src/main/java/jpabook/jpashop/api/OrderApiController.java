package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;
	
	@GetMapping("/api/v1/orders") //엔티티 직접노출
	public List<Order> ordersV1(){
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		
		for(Order order : all) {
			order.getMember().getName();
			order.getDelivery().getAddress();
		
			List<OrderItem> orderItems = order.getOrderItems();
			orderItems.stream().forEach(o -> o.getItem().getName());
		}
		return all;
	}

	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV2(){
		List<Order> orders = orderRepository.findAllByString(new OrderSearch());
		List<OrderDto> result = orders.stream()
				.map(o -> new OrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}
	
	@GetMapping("/api/v3/orders")
	public List<OrderDto> ordersV3(){
		List<Order> orders = orderRepository.findAllWithItem();
		List<OrderDto> result = orders.stream()
				.map(o -> new OrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}
	
	/*
	엔티티를 조회해서 DTO로 변환 페이징 고려
	- ToOne 관계만 우선 모두 페치 조인으로 최적화
	- 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
	*/
	@GetMapping("/api/v3.1/orders")
	public List<OrderDto> ordersV3_Page(
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "100") int limit){
		
		List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
		List<OrderDto> result = orders.stream()
				.map(o -> new OrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}
	
	@GetMapping("/api/v4/orders")
	public List<OrderQueryDto> ordersV4(){
		return orderQueryRepository.findOrderQueryDtos();
	}
	
	@Data
	static class OrderDto{
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		private List<OrderItemDto> orderItems;
		
		public OrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
			orderItems = order.getOrderItems().stream()
					.map(orderItem -> new OrderItemDto(orderItem))
					.collect(Collectors.toList());
		}
	}
	
	@Data
	static class OrderItemDto {
		
		private String itemName; //상품명
		private int orderPrice;  //상품가격
		private int count;		 //수량
		
		public OrderItemDto(OrderItem orderItem) {
			itemName = orderItem.getItem().getName();
			orderPrice = orderItem.getOrderPrice();
			count = orderItem.getCount();
		}
	}
}
