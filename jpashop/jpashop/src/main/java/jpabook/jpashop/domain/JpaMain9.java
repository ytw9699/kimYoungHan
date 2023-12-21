package jpabook.jpashop.domain;

import jpabook.jpashop.domain.jpql.Member7;
import jpabook.jpashop.domain.jpql.Team7;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain9 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Team7 team7 = new Team7();
                  team7.setName("team7");

            em.persist(team7);

            Member7 member = new Member7();
                    member.setUsername("member8");
                    member.setAge(10);

            member.setTeam7(team7);

            em.persist(member);

            em.flush();
            em.clear();

            List<Member7> result = em.createQuery("select m from Member7 m inner join m.team7 t", Member7.class)
                    .getResultList();

            for (Member7 member7 : result) {
                System.out.println("member7 = " + member7);
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
