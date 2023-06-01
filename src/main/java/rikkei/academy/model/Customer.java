package rikkei.academy.model;

import javax.persistence.*;

@Entity
@Table(name="customers")
public class Customer {
    // @Id--> Khóa chính là Id
    @Id
    // @GeneratedValue: Auto increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Customer() {
    }

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
