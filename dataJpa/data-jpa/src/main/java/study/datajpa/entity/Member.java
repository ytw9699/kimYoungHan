package study.datajpa.entity;

import lombok.*;
import javax.persistence.*;

@ToString(of = {"id", "username","age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter//Setter는 엔티티에 없는게 좋은데 예제라 일단 둠
@Getter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    public Member(String username){//셋터보다 이렇게 생성자로 하는게 더 나은방법
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
