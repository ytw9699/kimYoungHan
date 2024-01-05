package domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain10 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
                          tx.begin();
        try{

            Child2 child1 = new Child2();
            Child2 child2 = new Child2();

            Parent2 parent = new Parent2();
                    parent.addChild(child1);
                    parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent2 findParent = em.find(Parent2.class, parent.getId());
                   findParent.getChildList().remove(0);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
