package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository repository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = repository.save(member);
        Member findMember = repository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);//findMember == member와 같은 상황인데
        //Transactional이 같기에 같은 인스턴스
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        repository.save(member1);
        repository.save(member2);

        //단건 조회 검증
        Member findMember1 = repository.findById(member1.getId()).get();//편의상 옵셔널 일단 안씀
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
}