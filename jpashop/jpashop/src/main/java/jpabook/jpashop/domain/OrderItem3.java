package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class OrderItem3 {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private Order3 order;
    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private Item3 item;
    private int count;
    private int orderPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order3 getOrder() {
        return order;
    }

    public void setOrder(Order3 order) {
        this.order = order;
    }

    public Item3 getItem() {
        return item;
    }

    public void setItem(Item3 item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
