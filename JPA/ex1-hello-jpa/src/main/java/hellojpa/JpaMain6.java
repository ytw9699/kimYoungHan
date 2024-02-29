package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain6 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{
            Member member = new Member();
                   member.setId(1L);
                   member.setName("Hello1");
                   member.setRoleType(RoleType.USER);

            Member member2 = new Member();
            member2.setId(2L);
            member2.setName("Hello2");
            member2.setRoleType(RoleType.ADMIN);

            em.persist(member);
            em.persist(member2);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
