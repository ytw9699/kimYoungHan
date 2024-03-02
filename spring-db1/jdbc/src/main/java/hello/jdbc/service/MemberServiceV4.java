package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;//스프링에 의존하는것 예외추상화 계층에 의존
import org.springframework.transaction.annotation.Transactional;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 * MemberRepository 인터페이스 의존
 */
@Slf4j
public class MemberServiceV4 {

    private final MemberRepository memberRepository;

    public MemberServiceV4(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        try {//사실잡을일이 거의없지만 예시를 보여줌
            memberRepository.update(fromId, fromMember.getMoney() - money);
        } catch (DuplicateKeyException e){//jdbc 던 jpa 던 특정기술에 족송적인게 아니다
            //복구잡업 예시
        }
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
