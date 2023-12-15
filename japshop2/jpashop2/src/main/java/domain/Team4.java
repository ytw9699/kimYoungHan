package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team4 {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;
    
    @OneToMany
    @JoinColumn(name = "TEAM_ID")//JoinColumn사용안하면 조인테이블 방식 디폴트 사용하기에 JoinColumn사용하는게 좋음
    private List<Member4> members = new ArrayList<>();

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

    public List<Member4> getMembers() {
        return members;
    }

    public void setMembers(List<Member4> members) {
        this.members = members;
    }
}
