package hellojpa;

import javax.persistence.*;
import java.util.List;
public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //hello는 persistence의  <persistence-unit name="hello">
        //로딩시점에 하나만 만들어야 한다. 어플리케이션 전체에서 공유, 데이터베이스당 하나씩묶여서 돌아감

        EntityManager em = emf.createEntityManager();
        //일관적인 단위를 할때마다  EntityManager를 만들어 줘야함. 디비 커넥션 물고 동작, 쓰레드간에 공유 사용하고 버려야함

        EntityTransaction tx = em.getTransaction();
                          tx.begin(); //jpa 모든 데이터 변경은 트랜잭션 안에서 실행
        try{
            /*Member member = new Member();
            member.setId(2L);
            member.setName("Hello1");
            em.persist(member);*/

            Member findMember = em.find(Member.class, 2L);

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                                    .setFirstResult(0)
                                    .setMaxResults(8)
                                    .getResultList();

            for (Member meber : result){
                System.out.println(meber.getName());
            }

            findMember.setName("hello2");//이렇게만 해줘도 수정됨

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();//닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();//리소스가 릴리즈 됨
    }
}
