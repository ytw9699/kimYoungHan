package domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Child {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Parent parent;
}

