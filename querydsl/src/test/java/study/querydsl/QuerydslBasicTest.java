package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @PersistenceUnit
    EntityManagerFactory emf;

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
    
    @Test
    public void join(){//팀 a에소속된 모든 회원을 찾아라
        List<Member> result = queryFactory
                                .selectFrom(member)
                                .innerJoin(member.team, team)//멤버와 팀 조인
                                .where(team.name.eq("teamA"))
                                .fetch();
        assertThat(result)
                    .extracting("username")
                    .containsExactly("member1", "member2");
    }


    @Test
    public void theta_join(){//세타조인
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Member> result = queryFactory
                                .select(member)
                                .from(member, team)//연관관계가 없어도 조인가능
                                .where(member.username.eq(team.name))
                                .fetch();
        assertThat(result)
                .extracting("username")
                .containsExactly("teamA","teamB");
    }

    @Test //jpql : select m, t from Member m left join m.team t on t.name = 'teamA'
    public void join_on_filtering(){//회원과 팀을 조인하면서 팀이름이 teama인 팀만 조인, 회원은 모두 조회
        List<Tuple> result = queryFactory
                            .select(member, team)
                            .from(member)
                            .leftJoin(member.team, team)
                            .on(team.name.eq("teamA"))
                            .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void join_on_no_relation(){
        //연관관계없는 엔티티 외부조인,회원과 팀을 조인하면서 팀이름이 teama인 팀만 조인, 회원은 모두 조회
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                            .select(member, team)
                            .from(member)
                            .leftJoin(team).on(member.username.eq(team.name))//id가 아니라 username으로만 조인
                            .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void fetchJoinNo(){
        em.flush();
        em.clear();

        Member findMember = queryFactory
                            .selectFrom(member)
                            .where(member.username.eq("member1"))
                            .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());//로딩된것인지 여부

        assertThat(loaded).as("페치 조인 미적용").isFalse();
    }

    @Test
    public void fetchJoinUse(){
        em.flush();
        em.clear();

        Member findMember = queryFactory
                            .selectFrom(member)
                            .join(member.team ,team).fetchJoin()//fetchJoin
                            .where(member.username.eq("member1"))
                            .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        assertThat(loaded).as("페치 조인 적용").isTrue();
    }

    @Test
    public void subQuery(){//나이가 가장 많은 회원 조회

        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                                .selectFrom(member)
                                .where(member.age.eq(
                                        select(memberSub.age.max())
                                                .from(memberSub)
                                ))
                                .fetch();

        assertThat(result)
                    .extracting("age")
                    .containsExactly(40);
    }

    @Test
    public void subQueryGoe(){//평균보다 큰거 >=

        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                                .selectFrom(member)
                                .where(member.age.goe
                                        (select(memberSub.age.avg()).from(memberSub)))
                                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactly(30,40);
    }

    @Test
    public void subQueryIn(){

        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(select(memberSub.age).from(memberSub).where(memberSub.age.gt(10))))
                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactly(20,30,40);
    }

    @Test
    public void selectSubQuery() {//유저이름과 평균나이 다뽑음. 예제는 의미없는데 select 에서 서브쿼리 사용예제
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                            .select(member.username, select(memberSub.age.avg()).from(memberSub))
                            .from(member)
                            .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void basicCase(){
        List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void complexCase() {//이렇게 하기보단 애플레케이션이나 프리젠테이션 계층에서 해결해야한다
        List<String> result = queryFactory
                                .select(new CaseBuilder()
                                    .when(member.age.between(0, 20)).then("0~20살")
                                    .when(member.age.between(21, 30)).then("21~30살")
                                    .otherwise("기타"))
                                .from(member)
                                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void constant(){
        List<Tuple> result = queryFactory
                            .select(member.username, Expressions.constant("A"))//무조건 상수 a가져옴
                            .from(member)
                            .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void concat() {//문자 더하기
        List<String> result = queryFactory
                            .select(member.username.concat("_").concat(member.age.stringValue()))
                            .from(member)
                            .where(member.username.eq("member1"))
                            .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void simpleProjection(){
        List<String> result = queryFactory
                                .select(member.username)
                                .from(member)
                                .fetch();

        System.out.println("result = " + result);
    }

    @Test
    public void tupleProjection() {//Tuple은 레포지토리까지는 쓰지만 서비스계층 까지넘어가면 좋지않음
        List<Tuple> result = queryFactory
                                .select(member.username, member.age)
                                .from(member)
                                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    @Test
    public void findDtoByJPQL(){
        List<MemberDto> result =
                em.createQuery(
                    "select new study.querydsl.dto.MemberDto(m.username,m.age) from Member m",
                                MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findDtoBySetter() {//setter방식
        List<MemberDto> result = queryFactory
                                .select(Projections.bean(MemberDto.class, member.username, member.age))
                                .from(member)
                                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findDtoByField() {//setter 없이 바로 값이 필드에 꽂힘. 라이브러리가 private 이어도 리플렉션등으로 해결
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findDtoByConstructor() {
        List<MemberDto> result = queryFactory
                        .select(Projections.constructor(MemberDto.class, member.username, member.age))
                        .from(member)
                        .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void findUserDto() {
        QMember memberSub = new QMember("memberSub");

        //이방식은 런타임에 오류를 발견하는 단점
        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),
                        //서브쿼리 이용해 최대 나이로만 모두 찍어보는것
                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub),"age")))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    public void findDtoByQueryProjection(){//컴파일시점에 오류를 발견하는 장점

        List<MemberDto> result = queryFactory
                                .select(new QMemberDto(member.username, member.age))
                                .from(member)
                                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void dynamicQuery_BooleanBuilder(){
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam,ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();

        if (usernameCond!=null){
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond!=null){
            builder.and(member.age.eq(ageCond));
        }
        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    public void dynamicQuery_whereParam(){//이런방식을 잘쓴다
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond),ageEq(ageCond))
              //.where(allEq(usernameCond,ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    public void bulkUpdate(){

        //member1 = 10 > db member1
        //member2 = 10 > db member2
        //member3 = 10 > db member3
        //member4 = 10 > db member4

        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        //member1 = 10 > db 비회원
        //member2 = 10 > db 비회원
        //member3 = 10 > db member3
        //member4 = 10 > db member4

        List<Member> result1 = queryFactory.selectFrom(member).fetch();
        System.out.println("result1 = " + result1);

        em.flush();
        em.clear();//벌크연산의 문제점으로 영속성 컨텍스트 초기화해줘야함

        List<Member> result2 = queryFactory.selectFrom(member).fetch();
        System.out.println("result2 = " + result2);

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void bulkAdd(){
        
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))//모든 회원 나이 한살 업
                .execute();


        List<Member> result1 = queryFactory.selectFrom(member).fetch();
        System.out.println("result1 = " + result1);

        em.flush();
        em.clear();//벌크연산의 문제점으로 영속성 컨텍스트 초기화해줘야함

        List<Member> result2 = queryFactory.selectFrom(member).fetch();
        System.out.println("result2 = " + result2);

        assertThat(count).isEqualTo(4);
    }

    @Test
    public void bulkMultiply(){

        long count = queryFactory
                .update(member)
                .set(member.age, member.age.multiply(2))
                .execute();


        List<Member> result1 = queryFactory.selectFrom(member).fetch();
        System.out.println("result1 = " + result1);

        em.flush();
        em.clear();

        List<Member> result2 = queryFactory.selectFrom(member).fetch();
        System.out.println("result2 = " + result2);

        assertThat(count).isEqualTo(4);
    }

    @Test
    public void bulkDelete(){
        long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();

        List<Member> result1 = queryFactory.selectFrom(member).fetch();
        System.out.println("result1 = " + result1);

        em.flush();
        em.clear();

        List<Member> result2 = queryFactory.selectFrom(member).fetch();
        System.out.println("result2 = " + result2);


        assertThat(count).isEqualTo(3);
    }

    @Test
    public void sqlFunction(){
        List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})",
                            member.username, "member", "M"))//멤버는 m으로 바궈서 조회
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction2(){//소문자로 변경해서 비교
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
//  .where(member.username.eq(Expressions.stringTemplate("function('lower', {0})", member.username)))
                .where(member.username.eq(member.username.lower()))
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }

    }
}
