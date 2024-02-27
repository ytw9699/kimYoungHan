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
        //고객의 요청이 들어와서 만들고 요청이 나가면 버림

        //jpa 모든 데이터 변경은 트랜잭션 안에서 실행. 조회는 예외
        EntityTransaction tx = em.getTransaction();//트랜잭션 얻음
                          tx.begin();//트랜잭션 시작
        try{
            Member member = new Member();
                   member.setId(1L);
                   member.setName("Hello1");//여기까진 아직 비영속 상태

            System.out.println("===Before===");
            em.persist(member);//엔티티를 영속성 컨텍스트에 저장한다는것이다. 영속상태가 됨.
            //디비에 저장한다는 뜻보다는 영속성컨텍스트를 통해서 엔티티를 영속화 하는것
            //em.detach(member);//영속성 컨텍스트에서 삭제함. 준영속 상태
            //em.remove(member);//실제 디비에서 영구저장한것을 삭제 요청하는것
            System.out.println("===After===");

            Member findMember = em.find(Member.class, 1L);//단순한 조회. 이때 1차캐시에서 조회함
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

            //테이블이 아닌 멤버 엔티티 객체를 대상으로 쿼리를 만듬. 객체지향 쿼리
            //JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)//멤버 객체를 다가져와라
                                    .setFirstResult(0)//0번 부터 8까지 페이징하는데 그것도 방언에 맞춰서 알아서 쿼리 변경
                                    .setMaxResults(8)
                                    .getResultList();

            for (Member meber : result){
                System.out.println(meber.getName());
            }

            findMember.setName("hello2");//이렇게만 해줘도 수정됨. persist안해도됨
            tx.commit();//jpa가 엔티티를 관리하면서 변경이되었는지 커밋할때 체크함. 변경되면 업데이트하고 커밋함. 이때 쿼리가 날라감

        }catch (Exception e){
            tx.rollback();//문제가 생기면 롤백
        }finally {
            em.close();//작업끝나면 닫아줘야 데이터베이스 커넥션 반환
        }
        emf.close();
        //전체 애플리케이션이끝나면 EntityManagerFactory닫음. 리소스가 릴리즈 됨. 웹어플리케이션이면 와스가 내려갈때 닫아야함
    }
}
