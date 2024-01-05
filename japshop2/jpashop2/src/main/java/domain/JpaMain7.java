package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain7 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{
            Member7 member7 = new Member7();
            member7.setUsername("member7");

            em.persist(member7);
            em.flush();
            em.clear();

            //Member7 findMember = em.find(Member7.class, member7.getId());//진짜 객체
            Member7 findMember = em.getReference(Member7.class, member7.getId());//가짜 객체
            System.out.println("findMember.getClass() = " + findMember.getClass());
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getUsername());//이때 실제 값을 디비서 조회

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
