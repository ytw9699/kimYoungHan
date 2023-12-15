package jpabook.jpashop.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin(); //jpa 모든 데이터 변경은 트랜잭션 안에서 실행
        try{

            OrderItem2 orderItem = new OrderItem2();
                       orderItem.setCount(1);
                       orderItem.setOrderPrice(1000);

            em.persist(orderItem);

            Order2 order = new Order2();
                   order.addOrderItem(orderItem);

            em.persist(order);

            //양방향 없이 이렇게 해도된다 사실상 애플리케이션 개발할때 큰 문제없음 단방향이어도!
            /*Order2 order2 = new Order2();
            em.persist(order2);

            OrderItem2 orderItem2 = new OrderItem2();
                       orderItem2.setOrder(order2);

            em.persist(orderItem2);*/

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();//닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();//리소스가 릴리즈 됨
    }
}
