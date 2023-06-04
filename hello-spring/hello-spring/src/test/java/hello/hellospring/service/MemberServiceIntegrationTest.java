package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.test.annotation.Commit;

@SpringBootTest
//스프링 컨테이너(주입을 받는장점활용)와 테스트를 함께 실행한다. 단위테스트가 아니라 통합테스트이기에 스프링이 띄어지는데 시간이 많이걸리는 단점.
//그래서 단위테스트를 만드는게 확률적으로 좋은 테스트일 확률이 높다.
@Transactional
//@Transactional 를 testcase에 달면 각각의 테스트를 시작전에 트랜잭션을 시작하고 db에 데이터를 다 넣은 다음에 테스트가 끝나면 롤백을 해줌
// -> db에 데이터가 남지 않으므로 테스트에 영향을 주지않는다. but @Transactional이 테스트케이스에 붙었을 때에만 해당한다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    //test는 다른곳에서 갖다 쓸 것이 아니기때문에 편하게 필드 기반으로 autowired하는 경우가 많음

    @Test
    //@Commit //이것은 진짜 커밋됨
    void join() {
        //given
        Member member = new Member();
        member.setName("spring1");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void duplicated_member_exception() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");

    }


}