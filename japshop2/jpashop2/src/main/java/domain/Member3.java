package domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Member3 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = LAZY)
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

    public void changeTeam(Team3 team3) {
        if (this.team3 != null) {//기존에 것을 먼저 삭제하고 넣어줘야함
            this.team3.getMembers().remove(this);
        }
        this.team3 = team3;
        team3.getMembers().add(this);//this는 나자신의 인스턴스를 넣어줌
    }
}
