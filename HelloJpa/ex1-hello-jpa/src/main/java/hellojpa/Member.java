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
    @Column(name="name")//맵핑
    private String name;

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
