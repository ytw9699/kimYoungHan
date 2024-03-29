package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class OrderItem2 {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private Order2 order;
    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private Item item;
    private int count;
    private int orderPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order2 getOrder() {
        return order;
    }

    public void setOrder(Order2 order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
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
