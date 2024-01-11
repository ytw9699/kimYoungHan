package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;
import java.util.List;

//스프링 데이터 jpa 레포지토리임
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsername(String username);//구현하지 않아도 동작. 쿼리메소드 기능!

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age")
    List<Member> findMember(@Param("username") String username, @Param("age") int age);
}
