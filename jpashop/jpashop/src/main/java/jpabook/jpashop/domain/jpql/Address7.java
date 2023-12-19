package jpabook.jpashop.domain.jpql;

import lombok.Data;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address7 {

    private String city;
    private String street;
    private String zipcode;
}
