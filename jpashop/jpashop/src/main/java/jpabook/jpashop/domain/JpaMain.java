package jpabook.jpashop.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin(); //jpa 모든 데이터 변경은 트랜잭션 안에서 실행
        try{
            Order order = em.find(Order.class, 1L);

            Long memberId = order.getMemberId();

            Member member = em.find(Member.class, memberId);
            //멤버를 가져오는게.. 객체지향적이지않음 .왜냐면 참조로 안찾음. 관계형디비를 객체에 맞춘거임

            System.out.println(member); //테이블 중심 설계의 문제점..

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();//닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();//리소스가 릴리즈 됨
    }
}
