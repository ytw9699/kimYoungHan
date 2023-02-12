package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member2 member = new Member2();
            member.setUsername("member1");

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member2 findMember = em.find(Member2.class, member.getId());
            Team findTeam = findMember.getTeam();

            System.out.println(11);
            System.out.println(findTeam.getName());
            System.out.println(22);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
