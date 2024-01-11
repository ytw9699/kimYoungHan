package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository repository;//스프링이 구현체를 인젝션 해줌
    @Autowired
    private TeamRepository teamRepository;

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

}




