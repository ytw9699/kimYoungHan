package hellojpa;

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
                Member member1 = new Member(150L,"A");
                Member member2 = new Member(160L,"A");

                em.persist(member1);
                em.flush();//영속성 컨텍스트의 변경 내용을 디비에 동기화. 디비에 쿼리까지만 날림. 하지만 트랜잭션이 커밋되는것은 아님
                em.persist(member2);

                System.out.println("================");

                tx.commit();//커밋시 자동으로 flush호출

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
