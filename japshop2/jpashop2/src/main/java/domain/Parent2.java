package domain;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Parent2 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child2> childList = new ArrayList<>();

    public void addChild(Child2 child){//편의 메소드
            childList.add(child);
            child.setParent(this);
    }
}

