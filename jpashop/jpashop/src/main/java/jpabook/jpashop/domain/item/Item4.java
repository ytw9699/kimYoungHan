package jpabook.jpashop.domain.item;

import javax.persistence.*;

@DiscriminatorColumn//단일테이블 전략은 이 어노테이션 없어도 DTYPE이 기본으로 들어감
@Inheritance(strategy = InheritanceType.JOINED)//조인전략
@Entity
public abstract class Item4 {//abstract추상 클래스로 만드는 이유는 아이템 테이블이 필요없는 경우가 있을수 있기때문
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
