package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

//jpa에서 모든 데이터변경이나 로직들은 가급적 Transactional안에서 실행되어야한다 그래야 레이지로딩등이 가능
@Transactional(readOnly = true)//public메소드들에 readOnly를 적용해서 성능을 높인다!
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /** 회원 가입 */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {//이렇게 해도 동시에 회원가입하면 문제가 생길수있으니 닉네임을 유니크로 해라
        List<Member> findMembers = memberRepository.findByName(member.getUsername());
        if (findMembers.size() > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /** 회원 전체 조회*/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /** 회원 단건 조회 */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
