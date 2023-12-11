package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //hello는 persistence의  <persistence-unit name="hello">
        //로딩시점에 하나만 만들어야 한다. 어플리케이션 전체에서 공유, 데이터베이스당 하나씩묶여서 돌아감

        EntityManager em = emf.createEntityManager();//쉽게 해석하면 데이터베이스 커넥션 하나 받았다고 생각
        //실제 디비에 저장하는 트랜잭션 단위처럼 고객의 행위(디비커넥션 얻고 쿼리날리고 종료)를 할때마다 EntityManager를 만들어 줘야함.
        //EntityManager가 내부적으로 디비 커넥션 물고 동작, 쓰레드간에 공유하면 안되고 사용하고 버려야함

        //jpa 모든 데이터 변경은 트랜잭션 안에서 실행. 조회는 예외
        EntityTransaction tx = em.getTransaction();//트랜잭션 얻음
                          tx.begin();//트랜잭션 시작
        try{
            Member member = new Member();
                   member.setId(1L);
                   member.setName("Hello1");

            em.persist(member);

            Member findMember = em.find(Member.class, 1L);//단순한 조회
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            //테이블이 아닌 멤버 엔티티 객체를 대상으로 쿼리를 만듬. 객체지향 쿼리
            List<Member> result = em.createQuery("select m from Member as m", Member.class)//멤버 객체를 다가져와라
                                    .setFirstResult(0)//0번 부터 8까지 페이징하는데 그것도 방언에 맞춰서 알아서 쿼리 변경
                                    .setMaxResults(8)
                                    .getResultList();

            for (Member meber : result){
                System.out.println(meber.getName());
            }

            findMember.setName("hello2");//이렇게만 해줘도 수정됨. persist안해도됨
            tx.commit();//jpa가 엔티티를 관리하면서 변경이되었는지 커밋할때 체크함. 변경되면 업데이트하고 커밋함

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();//작업끝나면 닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();//리소스가 릴리즈 됨. 웹어플리케이션이면 와스가 내려갈때 닫아야함
    }
}
