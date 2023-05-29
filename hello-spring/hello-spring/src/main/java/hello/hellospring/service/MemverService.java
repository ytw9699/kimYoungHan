package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemverService {

    private final MemberRepository memberRepository;

    public MemverService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입 : 같은 이름이 있는 중복 회원 안됨
     */
    public Long join(Member member){//네이밍 비지니스적

        validateDuplicateMember(member);

        memberRepository.save(member);

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
