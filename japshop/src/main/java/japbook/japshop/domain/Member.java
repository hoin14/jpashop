package japbook.japshop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;
	private Address address;
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
}
