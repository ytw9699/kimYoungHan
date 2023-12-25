package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    @Embedded//내장타입을 포함했다
    private Address address;
    @OneToMany(mappedBy = "member")//연관관계주인이아님. 하지만 양방향
    private List<Order> orders = new ArrayList<>();//읽기전용
}
