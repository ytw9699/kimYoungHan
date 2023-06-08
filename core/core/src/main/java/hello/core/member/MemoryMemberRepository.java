package hello.core.member;

import java.util.HashMap;
import java.util.Map;

//아직 db가 확정이 안됐기 때문에 memorymemberRepository를 만들었음
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();//동시에 접근할때는 concurrenthashmap써야함

    @Override
    public void save(Member member) {
        store.put(member.getId() member);
    }

    @Override
    public Member findById(Long memberId) {

        return store.get(memberId);
    }
}
