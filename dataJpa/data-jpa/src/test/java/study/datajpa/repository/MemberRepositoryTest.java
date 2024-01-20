package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository repository;//스프링이 구현체를 인젝션 해줌
    @Autowired
    private TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember(){
        System.out.println("MemberRepository = " + repository.getClass());
        Member member = new Member("memberA");
        Member savedMember = repository.save(member);

        Member findMember = repository.findById(savedMember.getId()).get();//옵셔널 쓰는건데 일단 대충 코딩

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        repository.save(member1);
        repository.save(member2);

        //단건 조회 검증
        Member findMember1 = repository.findById(member1.getId()).get();
        Member findMember2 = repository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = repository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = repository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        repository.delete(member1);
        repository.delete(member2);
        long deletedCount = repository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        // given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        repository.save(member1);
        repository.save(member2);

        // when
        List<Member> result = repository.findByUsernameAndAgeGreaterThan("AAA", 15);

        // then
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testFindMember() {
        // given
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        repository.save(member1);
        repository.save(member2);

        // when
        List<Member> result = repository.findMember("AAA", 10);

        // then
        assertThat(result.get(0)).isEqualTo(member1);
    }

    @Test
    public void testFindUsernameList() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);

        repository.save(m1);
        repository.save(m2);

        List<String> usernameList = repository.findUsernameList();

        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void testFindMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10);
        m1.setTeam(team);
        repository.save(m1);

        List<MemberDto> dataList = repository.findMemberDto();
        for (MemberDto memberDto : dataList) {
            System.out.println("MembetDto ==> " + memberDto);
        }
    }

    @Test
    public void testFindByNames() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);

        repository.saveAll(Arrays.asList(m1, m2));

        List<Member> members = repository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : members) {
            System.out.println("Member ==> " + member);
        }
    }


    @Test
    public void returnType() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        repository.save(m1);
        repository.save(m2);

        List<Member> result = repository.findListByUsername("AAA");//jpa에서 리스트는 데이터없어도 널 반환 안환다
        System.out.println("findListByUsername ==> " + result.size());

        Member findMember = repository.findMemberByUsername("AAA");//단건은 데이터없으면 null 반환됨
        System.out.println("findMember = " + findMember);

        //자바8이상의 경우, 데이터있을수도 없을수도 있으면 Optional쓰는게 맞음
        Optional<Member> result2 = repository.findOptionalMemberByUsername("AAA");
        System.out.println("result2 = " + result2);
    }

    @Test
    public void paging() {
        // given
        repository.save(new Member("member1", 10));
        repository.save(new Member("member2", 10));
        repository.save(new Member("member3", 10));
        repository.save(new Member("member4", 10));
        repository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest
                = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));//페이지는 0부터시작
        //0페이지에서 3개 가져와

        // when
        Page<Member> page = repository.findByAge(age, pageRequest);
        //총 카운트 쿼리 분리 : 구하게되면 조인도 같이하게되서 수동으로 적어줘야함
        //Slice<Member> page = repository.findByAge(age, pageRequest);
        //더보기 예제 , limit+1 주어서 추가 count쿼리없이 다음페이지 여부 확인
        List<Member> content = page.getContent();

        Page<MemberDto> dtoPage = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));
        //dto로 변경하는 방법

        // then
        assertThat(content.size()).isEqualTo(pageRequest.getPageSize());
        assertThat(page.getTotalElements()).isEqualTo(5);//총 카운트 
        assertThat(page.getNumber()).isEqualTo(pageRequest.getPageNumber());//현재 페이지
        assertThat(page.getTotalPages()).isEqualTo(2);//총페이지수
        assertThat(page.isFirst()).isTrue();//첫페이지냐
        assertThat(page.isLast()).isFalse();
        assertThat(page.hasNext()).isTrue();//다음페이지가 있냐
    }

    @Test
    public void bulkAUpdate() {
        // given
        repository.save(new Member("member1", 10));
        repository.save(new Member("member2", 19));
        repository.save(new Member("member4", 20));
        repository.save(new Member("member3", 21));
        repository.save(new Member("member5", 40));

        // when
        int resultCount = repository.bulkAgePlus(20);//JPQL이 나가기전에 flush를 한다
        //em.clear();

        List<Member> result =repository.findByUsername("member5");
        Member member5 = result.get(0);
        System.out.println("member5 = " + member5);

        // then
        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy() {
        // given
        // member1 -> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);

        repository.save(member1);
        repository.save(member2);

        em.flush();
        em.clear();

        // n+1문제 발생 쿼리가 10번더 나가면서 네크웍을 10번 더 타게되서 성능이 빠를수가 없음
        List<Member> members = repository.findAll();
        //List<Member> members = repository.findMemberFetchJoin();//그래서 페치조인으로 해결
        // List<Member> members = repository.findAll();
        //List<Member> members = repository.findByUsername("member1");

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());//프록시로 채워둠
            System.out.println("member.team = " + member.getTeam().getName());//실제 쿼리 나감
        }
    }

    // dirty checking에서는 원본과 변경 된 객체를 2개 유지
    // 최적화가 되어있어도 리소스를 더 소모
    @Test
    public void queryHint() {
        // given
        Member member1 = new Member("member1", 10);
        repository.save(member1);
        em.flush();
        em.clear();
        // when
        // Member findMember = repository.findById(member1.getId()).get();
        Member findMember = repository.findReadOnlyByUsername(member1.getUsername());
               findMember.setUsername("member2");
        em.flush();
    }

    @Test
    public void testLock() {
        // given
        Member member1 = new Member("member1", 10);
        repository.save(member1);
        em.flush();
        em.clear();

        // when
        List<Member> members = repository.findLockByUsername(member1.getUsername());
        //     select
        //        member0_.member_id as member_i1_0_,
        //        member0_.age as age2_0_,
        //        member0_.team_id as team_id4_0_,
        //        member0_.username as username3_0_
        //    from
        //        member member0_
        //    where
        //        member0_.username=? for update
    }
    @Test
    public void testCallCustom() {
        List<Member> members = repository.findMemberCustom();
    }

    @Test
    public void specBasic() {//복잡하니 쓰지마 걍 테스트용도
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 10, teamA);
        Member m2 = new Member("m2", 10, teamA);

        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();
        // when
        Specification<Member> spec = MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
        List<Member> result = repository.findAll(spec);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void queryByExample(){
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);

        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        //Probe : 필드에 데이터가 있는 실제 도메인 객체
        Member member = new Member("m1");
        Team team = new Team("teamA");
        member.setTeam(team);//이렇게하면 inner 조인 하게됨

        ExampleMatcher metcher = ExampleMatcher.matching()
                .withIgnorePaths("age");//null이 아니면 이렇게 무시시킴
        
        Example<Member> example = Example.of(member, metcher);

        List<Member> result = repository.findAll(example);

        assertThat(result.get(0).getUsername()).isEqualTo("m1");
    }

    @Test
    public void projections(){
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member m1 = new Member("m1", 0, teamA);
        Member m2 = new Member("m2", 0, teamA);

        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        //when
        List<UsernameOnlyDto> result = repository.findProjectionsByUsername("m1", UsernameOnlyDto.class);
        System.out.println("result.size() = " + result.size());
        for (UsernameOnlyDto dto : result) {
            System.out.println("dto.getUsername() = " + dto.getUsername());
        }
    }
}




