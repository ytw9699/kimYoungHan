package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;
import javax.persistence.EntityManager;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void beforeEach(){
        queryFactory = new JPAQueryFactory(em);//멀티스레드환경에서 동시성 문제없이 동작함
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamB);
        Member member4 = new Member("member4",40,teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test //member1을 찾아라
    public void startJPQL(){
        Member findMember
            = em.createQuery("select m from Member m where m.username =:username", Member.class)
            .setParameter("username", "member1").getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl(){
        Member findMember = queryFactory
            .select(member)
            .from(member)
            .where(member.username.eq("member1"))//파라미터 바인딩 처리
            .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void search(){
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    public void searchAndParam(){
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(10)
                )
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    public void resultFetch(){
//        List<Member> fetch = queryFactory
//            .selectFrom(member)
//            .fetch();
//
//        Member fetchOne = queryFactory
//            .selectFrom(member)
//            .fetchOne();
//
//        Member fetchFirst = queryFactory
//            .selectFrom(member)
//            .fetchFirst();

        QueryResults<Member> results = queryFactory//성능중요시할때는 이거쓰면안됨, 카운트까지구하는경우 쿼리 2방날려라
                .selectFrom(member)
                .fetchResults();
        results.getTotal();
        //results.getLimit();
        //results.getOffset();
        List<Member> content = results.getResults();

        long total = queryFactory
                .selectFrom(member)
                .fetchCount();
    }

    @Test
    public void sort(){

        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);//마지막은 null

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    public void paging1(){
        List<Member> result = queryFactory
                                .selectFrom(member)
                                .orderBy(member.username.desc())
                                .offset(1)//앞에 1개를 스킵
                                .limit(2)
                                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void paging2(){//실무에선 이것을 못쓸수있음 성능때매 카운트쿼리를 분리해야하기때문 where조건같은게 다붙기때문
        QueryResults<Member> result = queryFactory
                                        .selectFrom(member)
                                        .orderBy(member.username.desc())
                                        .offset(1)
                                        .limit(2)
                                        .fetchResults();

        assertThat(result.getTotal()).isEqualTo(4);
        assertThat(result.getLimit()).isEqualTo(2);
        assertThat(result.getOffset()).isEqualTo(1);
        assertThat(result.getResults().size()).isEqualTo(2);
    }

    @Test
    public void aggregation(){
        List<Tuple> result = queryFactory
                                    .select(member.count(),
                                            member.age.sum(),
                                            member.age.avg(),
                                            member.age.max(),
                                            member.age.min())
                                    .from(member)
                                    .fetch();
        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    @Test
    public void group() throws Exception{//팀의 이름과 각팀의 평균연령을 구해라
        List<Tuple> result = queryFactory
                                .select(team.name, member.age.avg())
                                .from(member)
                                .join(member.team, team)
                                .groupBy(team.name)
                                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }
}
