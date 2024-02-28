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
                //버퍼링(지연해서 모음) 쿼리를 모았다가 한방에 디비날림
                em.persist(member1);//쿼리는 쓰기지연 저장소에 담아둠
                em.flush();//영속성 컨텍스트의 변경 내용을 디비에 동기화. 디비에 쿼리까지만 날림. 하지만 트랜잭션이 커밋되는것은 아님
                //flush하여도 1차캐시는 지워지지 않음
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
