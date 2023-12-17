package domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Member7 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team7 team7;
}

