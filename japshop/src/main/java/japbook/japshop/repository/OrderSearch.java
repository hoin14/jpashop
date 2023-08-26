package japbook.japshop.repository;

import japbook.japshop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
	
	private String memberName;
	private OrderStatus orderStatus;
	
}
