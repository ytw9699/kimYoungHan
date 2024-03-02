package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain3 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
                Member member = em.find(Member.class, 150L);//영속상태

                member.setName("ZZZZZ");//이렇게 변경만 해도 업데이트 쿼리가 나중에 실행

                System.out.println("================");
                
                //em.detach(member);//준영속 상태라 업데이트 쿼리가 안나가게됨. 특정 엔티티만 준영속 상태만듬
                //em.clear();//영속성 컨텍스트 통째로 날림, 1차 캐시에서도 다 날림
                //em.close(); //영속성 컨텍스트 종료 : 디비 연결 종료, 캐시나 리소스 해제
                tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
