package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {//추상클래스로 한다 구현체를 가지고할것이기때문

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /*도메인 주도 설계시 엔티티자체가 해결할수있는것은 주로 엔티티 안에 비즈니스 로직을 넣는게 객체지향적이다.
    stockQuantity데이터가 있는쪽에서 핵심 비즈니스 로직이 있는게 응집도가있는것이다 관리가 하기가 좋음
    그래서 setter 를 빼고 핵심 비즈니스 메소드를 가지고 재고를 변경해야하는것이다*/
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
