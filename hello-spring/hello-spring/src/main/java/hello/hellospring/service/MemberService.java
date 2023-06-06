package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입 : 같은 이름이 있는 중복 회원 안됨
     */
    /*public Long join(Member member){//네이밍 비지니스적

        validateDuplicateMember(member);

        memberRepository.save(member);

        return member.getId();
    }*/

    public Long join(Member member){//회원가입 시간 측정 이런 로직들이 메소드 마다 들어가야한다면?필요한것은 AOP

        long start = System.currentTimeMillis();

        try {
            validateDuplicateMember(member);

            memberRepository.save(member);
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join =" + timeMs + "ms");
        }

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

           /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(member1 -> { throw new IllegalStateException("이미 존재하는 회원");
        });*/

        memberRepository.findByName(member.getName()).
                ifPresent(member1 -> {
                    throw new IllegalStateException("이미 존재하는 회원");
        });
    }

    /**
     * 전체회원조회
     */
    public List<Member> findMembers(){//네이밍 비지니스적
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }


}
