package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;//int 대신 Integer쓰는건 null로 들어갈수 있기 때문
    private Integer quantity;//수량은 0이 기본으로 들어갈수 있으니까 int 로 해도될듯

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {//id제외 생성자
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
