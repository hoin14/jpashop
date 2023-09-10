package japbook.japshop.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import japbook.japshop.domain.Order;
import japbook.japshop.repository.OrderRepository;
import japbook.japshop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1(){
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			order.getMember().getName(); //Lazy 강제초기화
			order.getDelivery().getAddress(); //Lazy 강제초기화
		}
		return all;
	}
}
