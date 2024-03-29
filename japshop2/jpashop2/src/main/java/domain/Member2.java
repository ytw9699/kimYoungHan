package domain;

import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Member2 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    /*@Column(name = "TEAM_ID")
    private Long teamId;*/

    @ManyToOne(fetch = LAZY)//멤버입장에서 many to one
    @JoinColumn(name = "TEAM_ID")//멤버테이블의 외래키와 매핑
    private Team team;//멤버는 n 이고 팀은 1이다. 외래키가 있는 곳이 연관관계의 주인이다. 진짜 매핑

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
