package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;
import java.util.List;

//스프링 데이터 jpa 레포지토리임
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsername(String username);//구현하지 않아도 동작. 쿼리메소드 기능!

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
