package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity//jpa가 로딩될때 jpa를 사용하는 애로인식하고 관리한다.
@Table(name = "Member")//맵핑
public class Member {
    @Id
    private Long id;
    @Column(name="name", unique = true, length = 20)//맵핑
    private String name;
    private int age;

    public Member(){} //jpa는 리플렉션을 쓰기에 기본생성자 있어야함

    public Member(Long id, String name){//생성자
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
