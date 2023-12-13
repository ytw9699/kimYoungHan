package domain;

import javax.persistence.*;

@Entity
public class Member3 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team3 team3;//멤버는 n 이고 팀은 1이다.

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

    public Team3 getTeam() {
        return team3;
    }

    public void setTeam(Team3 team3) {
        this.team3 = team3;
        team3.getMembers().add(this);//this는 나자신의 인스턴스를 넣어줌
    }
}
