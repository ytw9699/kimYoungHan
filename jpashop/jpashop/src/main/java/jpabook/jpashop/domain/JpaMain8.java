package jpabook.jpashop.domain;

import jpabook.jpashop.domain.jpql.Member7;
import jpabook.jpashop.domain.jpql.MemberDTO;

import javax.persistence.*;
import java.util.List;

public class JpaMain8 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{

            for (int i=0; i<100; i++ ) {
                Member7 member = new Member7();
                        member.setUsername("member8");
                        member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member7> result = em.createQuery("select m from Member7 m order by m.age desc", Member7.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size()" + result.size());

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
