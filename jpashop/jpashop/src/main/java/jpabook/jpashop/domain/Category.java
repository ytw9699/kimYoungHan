package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Category parent;//상위 카테고리 셀프매핑. 자식입장에서 부모하나
    @OneToMany(mappedBy = "parent")//양방향 차일드
    private List<Category> child = new ArrayList<>();
    @ManyToMany
    @JoinTable(name="CATEGORY_ITEM",
            joinColumns = @JoinColumn(name= "CATEGORY_ID"),//내가 조인해야하는거
            inverseJoinColumns = @JoinColumn(name="ITEM_ID"))//반대편이 조인해야하는거
    private List<Item3> items = new ArrayList<Item3>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChild() {
        return child;
    }

    public void setChild(List<Category> child) {
        this.child = child;
    }

    public List<Item3> getItems() {
        return items;
    }

    public void setItems(List<Item3> items) {
        this.items = items;
    }
}
