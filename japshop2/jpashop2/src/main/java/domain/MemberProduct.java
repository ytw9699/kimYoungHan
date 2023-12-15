package domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberProduct {//MemberProduct > orders 로 추후 이름변경
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member6 member;

    @ManyToOne
    @JoinColumn(name = "PROUDCT_ID")
    private Product product;
    private int count;
    private int price;
    private LocalDateTime orderDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member6 getMember() {
        return member;
    }

    public void setMember(Member6 member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
