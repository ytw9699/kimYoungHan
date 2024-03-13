package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_name", length = 10)//name = "item_name" 생략가능
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {//기본생성자있어야 프록시 기술쓸수있음 필수로 넣어줘야함
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
