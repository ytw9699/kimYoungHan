package jpabook.jpashop.domain;

import jpabook.jpashop.domain.jpql.Member7;
import jpabook.jpashop.domain.jpql.MemberDTO;

import javax.persistence.*;
import java.util.List;

public class JpaMain7 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            Member7 member = new Member7();
                    member.setUsername("member7");
                    member.setAge(10);
            em.persist(member);

            TypedQuery<Member7> query1 = em.createQuery("select m from Member7 as m", Member7.class);

            List<Member7> resultList = query1.getResultList();

            for (Member7 member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            TypedQuery<Member7> query2 = em.createQuery("select m from Member7 as m where m.id = 1L" , Member7.class);
            Member7 singleResult = query2.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            TypedQuery<String> query3 = em.createQuery("select m.username from Member7 as m", String.class);
            System.out.println("query3 = " + query3.getSingleResult());
            Query query4 = em.createQuery("select m.username, m.age from Member7 as m");//반환 타입 명확하지 않을때
            System.out.println("query4 = " + query4);

            Member7 singleResult1 = em.createQuery("select m from Member7 as m where m.username = :username" , Member7.class)
                                    .setParameter("username","member7")
                                    .getSingleResult();
            System.out.println("singleResult1 = " + singleResult1);

            List<MemberDTO>  result = em.createQuery("select new jpabook.jpashop.domain.jpql.MemberDTO(m.username, m.age) from Member7 as m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
