package japbook.japshop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import japbook.japshop.domain.Address;
import japbook.japshop.domain.Order;
import japbook.japshop.domain.OrderStatus;
import japbook.japshop.repository.OrderRepository;
import japbook.japshop.repository.OrderSearch;
import japbook.japshop.repository.order.simplequery.OrderSimpleQueryDto;
import japbook.japshop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	private final OrderSimpleQueryRepository simpleOrderRepository;
	
	@GetMapping("/api/v1/simple-orders") //Entity
	public List<Order> ordersV1(){
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			order.getMember().getName(); //Lazy 강제초기화
			order.getDelivery().getAddress(); //Lazy 강제초기화
		}
		return all;
	}

	@GetMapping("/api/v2/simple-orders") //DTO
	public List<SimpleOrderDto> ordersV2(){
		//Order 2개
		//N + 1 -> 1 + N(2)
		List<Order> orders = orderRepository.findAllByString(new OrderSearch());
		
		List<SimpleOrderDto> result = orders.stream()
				.map(o -> new SimpleOrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}
	
	@GetMapping("/api/v3/simple-orders") //fetch조인
	public List<SimpleOrderDto> ordersV3(){
		List<Order> orders = orderRepository.findAllWithMemberDelivery();
		
		List<SimpleOrderDto> result = orders.stream()
				.map(o -> new SimpleOrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}
	
	@GetMapping("/api/v4/simple-orders") 
	public List<OrderSimpleQueryDto> ordersV4(){		
		return simpleOrderRepository.findOrderDtos();
	}
	
	@Data
	static class SimpleOrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		
		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName(); //Lazy초기화
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress(); //Lazy초기화
		}
	}
	
}
