package japbook.japshop.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import japbook.japshop.domain.Address;
import japbook.japshop.domain.Delivery;
import japbook.japshop.domain.Member;
import japbook.japshop.domain.Order;
import japbook.japshop.domain.OrderItem;
import japbook.japshop.domain.item.Book;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {

	private final InitService initService;
	
	@PostConstruct
	public void init() {
		initService.dbInit1();
		initService.dbInit2();
	}
	
	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService{
		
		private final EntityManager em;
		
		public void dbInit1() {
			Member member = createMember("userA", "서울", "1", "1111");
			em.persist(member);
			
			Book book1 = createBook("JPA1 BOOK1", 10000, 100);
			em.persist(book1);
			
			Book book2 = createBook("JPA1 BOOK2", 20000, 100);
			em.persist(book2);
			
			OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

			Delivery delivery = createDelivery(member.getAddress());
			
			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
			em.persist(order);
		}
		
		public void dbInit2() {
			Member member = createMember("userB", "진주", "1", "1111");
			em.persist(member);
			
			Book book1 = createBook("SPRING BOOK1", 20000, 200);
			em.persist(book1);
			
			Book book2 = createBook("SPRING BOOK2", 40000, 300);
			em.persist(book2);
			
			OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

			Delivery delivery = createDelivery(member.getAddress());
			
			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
			em.persist(order);
		}
		
		private Member createMember(String name, String city, String street, String zipcode) {
			Member member = new Member();
			member.setName(name);
			member.setAddress(new Address(city, street, zipcode));
			return member;
		}
		
		private Book createBook(String name, int price, int stockQuantity) {
			Book book = new Book();
			book.setName(name);
			book.setPrice(price);
			book.setStockQuantity(stockQuantity);
			return book;
		}
		
		private Delivery createDelivery(Address address) {
			Delivery delivery = new Delivery();
			delivery.setAddress(address);
			return delivery;
		}
	}
}
