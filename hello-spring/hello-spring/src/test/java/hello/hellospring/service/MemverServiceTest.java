package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemverServiceTest {

    MemverService memverService;
    MemoryMemberRepository memberRepository;


    @BeforeEach
    public void beforeEach(){
        memberRepository =  new MemoryMemberRepository();
        memverService = new MemverService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입(){

        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memverService.join(member);

        //then
        Member findMember = memverService.findOne(saveId).get();

        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외(){

        //given
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        Long saveId = memverService.join(member);

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memverService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");

       /* try {
            memverService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
        }*/
    }



}