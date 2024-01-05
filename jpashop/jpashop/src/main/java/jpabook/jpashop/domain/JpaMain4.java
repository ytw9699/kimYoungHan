package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain4 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin(); //jpa 모든 데이터 변경은 트랜잭션 안에서 실행
        try{
            Movie movie = new Movie();
                  movie.setDirector("AAA");
                  movie.setActor("BBB");
                  movie.setName("CCC");
                  movie.setPrice(1000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("movie = " + findMovie);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();//닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();//리소스가 릴리즈 됨
    }
}
