package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    //build.gradle에서 data-jpa 라이브러리를 받으면
    //스프링부트가 자동으로 EntityManager라는걸 생성해줌(현재 db와 연결까지 해서)
    //우리는 만들어진것을 injection 받으면 됨.(properties에서 세팅해뒀기 때문에)
    //커넥션 정보들이랑 데이터베이스 정보들이 다 짬뽕을 해서 entitymanager라는걸 만들어줌.
    // ie) db통신등에 필요한 정보를 내부적으로 다 갖고있음.
    private final EntityManager em;
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {

        em.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {

        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    @Override
    public Optional<Member> findByName(String name) {
        //jpql : 테이블 대상이 아닌 객체(entity)를 대상으로 쿼리문을 날리는 것
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
