package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain4 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{
            Member4 member4 = new Member4();
                    member4.setUsername("member4");

            em.persist(member4);

            Team4 team4 = new Team4();
                  team4.setName("Team4");
                  team4.getMembers().add(member4);//이곳은 팀 테이블에 인서트 되는게 아님. 멤버테이블의 외래키를 업데이트침

            em.persist(team4);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
