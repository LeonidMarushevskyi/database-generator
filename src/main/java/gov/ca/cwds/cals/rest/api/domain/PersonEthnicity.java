package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonEthnicity.
 */
@Entity
@Table(name = "person_ethnicity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonEthnicity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private EthnicityType ethnicity;

    @ManyToOne
    private EthnicityType subEthnicity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EthnicityType getEthnicity() {
        return ethnicity;
    }

    public PersonEthnicity ethnicity(EthnicityType ethnicityType) {
        this.ethnicity = ethnicityType;
        return this;
    }

    public void setEthnicity(EthnicityType ethnicityType) {
        this.ethnicity = ethnicityType;
    }

    public EthnicityType getSubEthnicity() {
        return subEthnicity;
    }

    public PersonEthnicity subEthnicity(EthnicityType ethnicityType) {
        this.subEthnicity = ethnicityType;
        return this;
    }

    public void setSubEthnicity(EthnicityType ethnicityType) {
        this.subEthnicity = ethnicityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonEthnicity personEthnicity = (PersonEthnicity) o;
        if (personEthnicity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personEthnicity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonEthnicity{" +
            "id=" + id +
            '}';
    }
}
