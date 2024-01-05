package jpabook.jpashop.domain.jpql;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Order7 {
    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;
    @Embedded
    private Address7 address7;

    @ManyToOne
    @JoinColumn(name = "RPODUCT_ID")
    private Product7 product7;
}
