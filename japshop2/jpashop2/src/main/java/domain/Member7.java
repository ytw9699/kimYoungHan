package domain;

import lombok.Data;
import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Member7 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team7 team7;
}

