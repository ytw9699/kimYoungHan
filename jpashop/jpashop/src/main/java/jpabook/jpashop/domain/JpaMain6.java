package jpabook.jpashop.domain;

import jpabook.jpashop.domain.embed.Address;
import jpabook.jpashop.domain.embed.Period;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain6 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin(); //jpa 모든 데이터 변경은 트랜잭션 안에서 실행
        try{
            Member6 member = new Member6();
                    member.setUserName("Member6");
                    member.setHomeAddress(new Address("city","street", "10000"));
                    member.setWorkPeriod(new Period());

            em.persist(member);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();//닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();//리소스가 릴리즈 됨
    }
}
