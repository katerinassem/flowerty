package by.itechart.flowerty.persistence.model;

import javax.persistence.*;

/**
 * @author Maria
 * Date: 20.03.15
 */
@Entity
@Table(name = "state")
public class State {

    private Long id;
    private DESCRIPTION_TYPE description;

    public State() {}

    public State(DESCRIPTION_TYPE description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 10, nullable = false)
    public Long getId() {
        return id;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "DESCRIPTION", nullable = false)
    public DESCRIPTION_TYPE getDescription() {
        return description;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setDescription(DESCRIPTION_TYPE description) {
        this.description = description;
    }

    public static enum DESCRIPTION_TYPE{
        ACCEPTED,
        CANCELED,
        CLOSED,
        DELIVERY,
        IMPOSSIBLE,
        NEW,
        PROCESSING,
        READY
    }

    @Override
    public String toString() {
        return new StringBuilder("State{")
                .append("id=").append(id)
                .append(", description=").append(description)
                .append('}').toString();
    }
}
