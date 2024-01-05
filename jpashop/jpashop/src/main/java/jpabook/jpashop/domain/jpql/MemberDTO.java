package jpabook.jpashop.domain.jpql;

import lombok.Data;

@Data
public class MemberDTO {
    private String username;
    private int age;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
