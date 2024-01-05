package jpabook.jpashop.domain.jpql;

import lombok.Data;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Member7 {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team7 team7;

    @Override
    public String toString() {
        return "Member7{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
