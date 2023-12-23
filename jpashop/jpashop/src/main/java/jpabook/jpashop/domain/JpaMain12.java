package jpabook.jpashop.domain;

import jpabook.jpashop.domain.jpql.Member7;
import jpabook.jpashop.domain.jpql.Team7;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain12 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team7 teamA = new Team7();
                  teamA.setName("팀A");
            em.persist(teamA);

            Member7 member1 = new Member7();
                    member1.setUsername("회원1");
                    member1.setTeam7(teamA);
            em.persist(member1);

            em.flush();
            em.clear();

            Member7 result = em.createQuery("select m from Member7 m where m = :member", Member7.class)
                .setParameter("member", member1)
                .getSingleResult();

            System.out.println("result = " + result);

            Member7 result2 = em.createQuery("select m from Member7 m where m.id = :memberId", Member7.class)
                    .setParameter("memberId", member1.getId())
                    .getSingleResult();

            System.out.println("result2 = " + result2);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
