package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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

            em.flush();//디비로 먼저 날림
            em.clear();//영속성 컨텍스트 초기화

            Member2 findMember = em.find(Member2.class, member.getId());
            //1차캐시에서 가져오는데, 위에서 clear 초기화해버려서디비에서가져옴
            Team findTeam = findMember.getTeam();

            System.out.println(11);
            System.out.println(findTeam.getName());
            System.out.println(22);
            
            //Team newTeam = em.find(Team.class, 100L); 
            //findMember.setTeam(newTeam);// 이렇게 팀을 바꾼다

            List<Member2> members = findMember.getTeam().getMembers();

            for (Member2 m : members){
                System.out.println(m.getUsername());
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
