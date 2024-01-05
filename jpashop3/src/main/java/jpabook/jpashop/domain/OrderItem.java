package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)//파라미터가 없는 디폴트 생성자를 PROTECTED로 생성
//이렇게 되면 직접 생성을 못하고, 다른 스타일로 생성메서드를 이용해야 하는구나 알게됨, 이렇게 제약하는 스타일이 좋은 유지보수와 설계를 가져옴
@Entity
@Getter @Setter
public class OrderItem {

    /*protected OrderItem(){//파라미터가 없는 디폴트 생성자를 PROTECTED로 생성

    }*/

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {//orderPrice는 쿠폰이나 할인등 고려
        
        OrderItem orderItem = new OrderItem();
                  orderItem.setItem(item);
                  orderItem.setOrderPrice(orderPrice);
                  orderItem.setCount(count);

        item.removeStock(count);//재고 까줌

        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {//재고를 다시 늘려줌
        getItem().addStock(count);
    }

    //==조회 로직==//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
