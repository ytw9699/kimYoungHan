package domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Member8 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)//프록시 객체로 조회. ManyToOne과 OneToOne기본이 즉시니까 이렇게 설정해야함
    //@ManyToOne(fetch = FetchType.EAGER)//즉시 로딩. 이방법은 거의 쓰지 말아라 무조건 지연로딩이라 생각해
    @JoinColumn(name = "TEAM_ID")
    private Team8 team8;
}

