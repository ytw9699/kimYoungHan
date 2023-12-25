package jpabook.jpashop.domain;

import lombok.Getter;
import javax.persistence.Embeddable;

@Embeddable//jpa 내장타입
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {//jpa는 리플렉션이나 프록시 기술을 써야할때가 있어서 기본생성자 필요
    }//new로 열지않음으로써 아래 메소드를 사용하도록 유도

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
