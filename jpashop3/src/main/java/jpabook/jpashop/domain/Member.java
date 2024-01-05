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
    //이렇게 초기화 해둬야 나중에 null 포인트 이셉션 발생도 미리 막고, 이걸로 메모리 얼마 먹지도않음. 이게 베스트 프랙티스임
    // 그리고 이 orders 컬렉션을 가급적 변경하면 안된다. 왜냐면 하이버네이트가 감싸서 관리하는 컬렉션으로 바꿔서 사용하고 있기때문
}
