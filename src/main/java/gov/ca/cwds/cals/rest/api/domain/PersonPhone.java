package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonPhone.
 */
@Entity
@Table(name = "person_phone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Person person;

    @OneToOne
    @JoinColumn(unique = true)
    private Phone phone;

    @ManyToOne
    private PhoneType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public PersonPhone person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Phone getPhone() {
        return phone;
    }

    public PersonPhone phone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public PhoneType getType() {
        return type;
    }

    public PersonPhone type(PhoneType phoneType) {
        this.type = phoneType;
        return this;
    }

    public void setType(PhoneType phoneType) {
        this.type = phoneType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonPhone personPhone = (PersonPhone) o;
        if (personPhone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personPhone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonPhone{" +
            "id=" + getId() +
            "}";
    }
}
