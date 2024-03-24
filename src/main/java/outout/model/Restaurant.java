package outout.model;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_generator")
    @SequenceGenerator(name = "restaurant_generator", sequenceName = "restaurant_seq")
    private Long id;

    @Column
    private String name;


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
