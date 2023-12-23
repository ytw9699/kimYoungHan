package jpabook.jpashop.domain;

import jpabook.jpashop.domain.jpql.Member7;
import jpabook.jpashop.domain.jpql.Team7;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain10 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team7 teamA = new Team7();
                  teamA.setName("팀A");
            em.persist(teamA);

            Team7 teamB = new Team7();
                  teamB.setName("팀B");
            em.persist(teamB);

            Member7 member1 = new Member7();
                    member1.setUsername("회원1");
                    member1.setTeam7(teamA);
            em.persist(member1);

            Member7 member2 = new Member7();
                    member2.setUsername("회원2");
                    member2.setTeam7(teamA);
            em.persist(member2);

            Member7 member3 = new Member7();
                    member3.setUsername("회원3");
                    member3.setTeam7(teamB);
                    em.persist(member3);

            em.flush();
            em.clear();

            //List<Member7> result = em.createQuery("select m from Member7 m inner join m.team7 t", Member7.class)
            //그냥 조인과 페치조인은 결과가 다르다!
            List<Member7> result = em.createQuery("select m from Member7 m join fetch m.team7", Member7.class)
                    .getResultList();

            for (Member7 member7 : result) {
                System.out.println("getUsername = " + member7.getUsername());//레이지 로딩으로 이때 조회쿼리나감
                System.out.println("getName = " + member7.getTeam7().getName());
                //회원1, 팀A - SQL
                //회원2, 팀A - 1차캐시
                //회원3, 팀B - SQL
                //이때 N+1 문제가 발생하게됨 그것을 페치조인으로 풀어라. 조인하면서 fetch로 한꺼번에 가져와
            }

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
