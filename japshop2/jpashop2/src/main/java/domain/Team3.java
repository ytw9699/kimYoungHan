package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team3 {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;
    
    //팀은 1 멤버는 다  > 1:다
    @OneToMany(mappedBy = "team3")//멤버테이블의 변수명. mappedBy의 정체는
    private List<Member3> members = new ArrayList<>();//add 할때 null포인트 안뜨게 초기화

    public List<Member3> getMembers() {
        return members;
    }

    public void setMembers(List<Member3> members) {
        this.members = members;
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
