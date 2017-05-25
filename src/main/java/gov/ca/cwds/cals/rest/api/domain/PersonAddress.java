package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonAddress.
 */
@Entity
@Table(name = "person_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Person person;

    @OneToOne
    @JoinColumn(unique = true)
    private Address race;

    @ManyToOne
    private AddressType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public PersonAddress person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getRace() {
        return race;
    }

    public PersonAddress race(Address address) {
        this.race = address;
        return this;
    }

    public void setRace(Address address) {
        this.race = address;
    }

    public AddressType getType() {
        return type;
    }

    public PersonAddress type(AddressType addressType) {
        this.type = addressType;
        return this;
    }

    public void setType(AddressType addressType) {
        this.type = addressType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonAddress personAddress = (PersonAddress) o;
        if (personAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonAddress{" +
            "id=" + getId() +
            "}";
    }
}
