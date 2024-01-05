package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain9 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
                   parent.addChild(child1);
                   parent.addChild(child2);

            em.persist(parent);
            //em.persist(child1);//이렇게 반복해서 persist해야하는걸 cascade = CascadeType.ALL로 해결
            //em.persist(child2);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
