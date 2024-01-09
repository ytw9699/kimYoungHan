package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

//스프링 데이터 jpa 레포지토리임
public interface MemberRepository extends JpaRepository<Member, Long> {
}
