package jpabook.jpashop.domain.item;

import javax.persistence.Entity;

@Entity
public class Book extends Item4{
    private String author;
    private String isbn;//번호
}
