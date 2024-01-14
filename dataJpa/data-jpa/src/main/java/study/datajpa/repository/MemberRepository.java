package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//스프링 데이터 jpa 레포지토리임
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsername(String username);//구현하지 않아도 동작. 쿼리메소드 기능!

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.age = :age")
    List<Member> findMember(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username);

    Member findMemberByUsername(String username);

    Optional<Member> findOptionalMemberByUsername(String username);

    Page<Member> findByAge(int age, Pageable pageable);
    // Slice<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 영속성 컨텍스트 초기화
    //@Modifying//executeUpdate업데이트 실행해줌
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();//페치조인은 연관된 팀을 한방쿼리로 모든 데이터 다끌고옴

    // 공통 메소드 오버라이드
    @Override
    @EntityGraph(attributePaths = { "team" })
    List<Member> findAll();//내부적으로 페치조인씀. jpql없이도 객체그래프를 한번에 엮어서 성능최적화해서 가져옴

    @EntityGraph(attributePaths = { "team" })
    @Query("select m from Member m")//위와 결과는 같음
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = { "team" })
    List<Member> findByUsername2(@Param("username") String username);
}
