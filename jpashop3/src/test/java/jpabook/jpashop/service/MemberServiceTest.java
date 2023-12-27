package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)////스트링과 통합테스트
@SpringBootTest//스트링과 통합테스트
@Transactional//커밋안하고 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    //@Rollback(value = false)
    public void 회원가입() throws Exception {
        //given 이런게 주어졌을때
        Member member = new Member();
        member.setUsername("kim");

        //when 이렇게 하면
        Long savedId = memberService.join(member);

        //then 결과가 이래야 한다
        em.flush();//이렇게 하면 일단 insert까지 나감
        assertEquals(member, memberRepository.findOne(savedId));
        //true가 나오는 이유는 같은 jpa에서 같은 Transactional안에서 pk값이 똑같으면
        //같은 영속성 컨텍스트에서 똑같은 엔티티가 관리가 됨
    }

    //  @Test(expected = IllegalStateException.class)
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); //예외가 발생해야 한다!!!
        } catch (IllegalStateException e){
            return;
        }
        //then
        fail("예외가 발생해야 한다. 테스트 실패!");
    }
}