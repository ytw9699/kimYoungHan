package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;
    
    //팀은 1 멤버는 다  > 1:다
    @OneToMany(mappedBy = "team")//멤버테이블의 변수명. 주인의 반대편 가짜 매핑. 읽기만 가능
    private List<Member2> members = new ArrayList<>();//add 할때 null포인트 안뜨게 초기화

    public List<Member2> getMembers() {
        return members;
    }

    public void setMembers(List<Member2> members) {
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
