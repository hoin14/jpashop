package japbook.japshop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

	@Autowired private MemberRepository memberRepository;
	
	@Test
	public void testMember() throws Exception{
		//given
		Member member = new Member();
		member.setUsername("memberA");
		
		//when
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.find(saveId);
		
		//then
		assertThat(findMember.getId()).isEqualTo(saveId);
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
		System.out.println("findmebmer == member:" + (findMember == member));
	}

}
