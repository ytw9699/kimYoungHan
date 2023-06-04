package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //MemberRepository 다중상속
    //구현체 자동등록해준다.

    @Override
    Optional<Member> findByName(String name);//구현할것 없다.
    //JPQL : select m from Member m where m.name = ?
}
