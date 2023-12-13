package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order2 {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne//하나의 회원은 여러번 주문, 주문의 입장에선 나를 주문한 회원은 하나
    @JoinColumn(name="MEMBER_ID")
    private Member2 member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem2> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addOrderItem(OrderItem2 orderItem2) {
        orderItems.add(orderItem2);
        orderItem2.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member2 getMember() {
        return member;
    }

    public void setMember(Member2 member) {
        this.member = member;
    }

    public List<OrderItem2> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem2> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
