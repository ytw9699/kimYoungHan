package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain5 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            String jpql = "select m from Member as m where m.name like '%kim%'";
            //문자열이라 동적쿼리만들기 힘듬..문자를 더할때 버그많이남. 마이바티스는 동적쿼리 편하게 짠다
            List<Member> result =
                    em.createQuery(jpql, Member.class)
            .getResultList();

            for (Member member : result) {
                System.out.println("member = " + member);
            }

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
