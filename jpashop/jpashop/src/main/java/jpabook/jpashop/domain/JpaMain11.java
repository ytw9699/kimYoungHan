package jpabook.jpashop.domain;

import jpabook.jpashop.domain.jpql.Member7;
import jpabook.jpashop.domain.jpql.Team7;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain11 {
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

            //List<Team7> result = em.createQuery("select t from Team7 t join fetch t.members", Team7.class)
            //팀 A가 중복출력되는 문제. 일대다 조인이라 데이터가 뻥튀기
            List<Team7> result = em.createQuery("select distinct t from Team7 t join fetch t.members", Team7.class)
                    .getResultList();

            for (Team7 team : result) {
                System.out.println("getName = " + team.getName());
                System.out.println("size = " + team.getMembers().size());
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
