package domain;

import lombok.Data;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Child2 {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="parent_id")
    private Parent2 parent;
}

