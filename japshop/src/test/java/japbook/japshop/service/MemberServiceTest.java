package japbook.japshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	public void 회원가입() throws Exception{
		//given
		Member member = new Member();
		member.setName("kim");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		em.flush();
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	@Test
	public void 중복_회원_예외() throws Exception{
		//given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		//when
		memberService.join(member1);
		try {
			memberService.join(member2);
		} catch (IllegalStateException e) {
			return;
		}
		
		//then
		fail("Exception");
	}
	
}
