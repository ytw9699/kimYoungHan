package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;

@Slf4j
@Repository
public class MemberRepository {//저장하고 관리하는 저장소

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L;//static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

  /*  public Optional<Member> findByLoginId2(String loginId) {
        List<Member> all = findAll();
        for (Member member : all) {
            if(member.getLoginId().equals(loginId)){
                return Optional.of(member);
            }
        }
        return Optional.empty();//값이없으면 null 대신쓰임
    }*/

    public Optional<Member> findByLoginId(String loginId) {
        //람다사용 ,리스트를 스트림으로 바꾸고 필터로 만족하지 않으면 버림
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();//루프를 돌다가 먼저 나오는 객체가 있으면 그것을 반환
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());//값만 뽑아 리스트로
    }

    public void clearStore() {//테스트시 초기화
        store.clear();
    }
}
