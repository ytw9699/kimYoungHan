package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain8 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{
            Team8 team = new Team8();
                  team.setName("Team8");
            em.persist(team);

            Member8 member = new Member8();
                    member.setUsername("member8");
                    member.setTeam8(team);

            em.persist(member);

            em.flush();
            em.clear();

            Member8 m = em.find(Member8.class, member.getId());
            System.out.println("m.getTeam8().getClass() = " + m.getTeam8().getClass());

            System.out.println("===========");
            m.getTeam8().getName();
            System.out.println("===========");

            //List<Member8> members = em.createQuery("select m from Member8 m", Member8.class).getResultList();
            //List<Member8> members = em.createQuery("select m from Member8 m join fetch m.team8", Member8.class).getResultList();

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
