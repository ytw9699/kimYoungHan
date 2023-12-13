package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain3 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{
            Team3 team3 = new Team3();
                  team3.setName("Team3");

            em.persist(team3);

            Member3 member3 = new Member3();
                    member3.setUsername("member3");
                    member3.changeTeam(team3);//이렇게 해줘야 TEAM_ID 외래키가 들어감
                    
            em.persist(member3);

            //team3.getMembers().add(member3);//양쪽으로 셋팅 이렇게 해야 객체지향적인것이고 테스트 케이스 작성할때도 jpa 없이 가능
            //위 로직 대신 member3 엔티티 들어가서 setTeam할때 같이 셋팅

            //em.flush();
            //em.clear();

            Team3 findTeam = em.find(Team3.class, team3.getId());//1차캐시에서 가져옴
            List<Member3> members = findTeam.getMembers();//만약 캐시에서 날리면 이 시점에 멤버테이블서 데이터 가져옴

            System.out.println(" =================");
            for (Member3 m : members){
                System.out.println("m =" + m.getUsername());
            }
            System.out.println(" =================");

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
