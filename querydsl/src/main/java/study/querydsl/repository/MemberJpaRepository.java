package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.entity.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import static study.querydsl.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {
    private final EntityManager em;//이 빈을 멀티스레드에서 써도 동시성 문제없음 트랜잭션단위로 따로 분리됨
    private final JPAQueryFactory queryFactory;

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);//널일수도있음
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findAll_Querydsl(){
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username =:username", Member.class)
            .setParameter("username",username).getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        return queryFactory
            .selectFrom(member)
            .where(member.username.eq(username))
            .fetch();
    }
}
