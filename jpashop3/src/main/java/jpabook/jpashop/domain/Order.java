package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")//맵핑 외래키
    private Member member;//연관관계주인. 이곳의 멤버를 바꾸면 다른멤버의 아이디로 외래키가 설정됨
    //lazy 여도 가짜 프록시 멤버 객체를 만들어둠

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//order 를 persist 하면 OrderItem도 해줌
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;//연관관계의주인

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드는 핵심적으로 컨트롤 하는쪽에서 들고있는게 좋다==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);//양방향이기때문
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);//양방향이기때문
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);//양방향이기때문
    }

    /**
     * 주문 생성
     */
    //==비즈니스 로직 응집해둠==//
    //==생성 메서드, 앞으로 생성시 무엇이 변경되면 이 메소드만 바꾸면 됨 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //실무에서는 훨씬복잡함. OrderItem도 그냥 넘어노느게 아니라 dto가 넘어올수도 있음.그리고 OrderItem도 이안에서 생성할수도 있음 
        
        Order order = new Order();
              order.setMember(member);
              order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {//이 체크로직도 엔티티 안에있다!
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);//오더상태 변경

        for (OrderItem orderItem : orderItems) {//재고원복
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public int getTotalPrice2() {//위 getTotalPrice() 로직과 같음
        return orderItems.stream()
                          .mapToInt(OrderItem::getTotalPrice)
                          .sum();
    }

}
