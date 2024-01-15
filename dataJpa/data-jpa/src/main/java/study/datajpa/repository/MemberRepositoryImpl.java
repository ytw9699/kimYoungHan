package study.datajpa.repository;

import java.util.List;
import javax.persistence.EntityManager;
import study.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;

// 네이밍은 해당 "repository name + Impl" 로 해야함
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}