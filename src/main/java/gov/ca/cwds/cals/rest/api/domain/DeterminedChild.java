package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DeterminedChild.
 */
@Entity
@Table(name = "determined_child")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeterminedChild implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_of_placement", nullable = false)
    private LocalDate dateOfPlacement;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @ManyToOne(optional = false)
    @NotNull
    private CountyType countyOfJurisdiction;

    @ManyToOne
    private Application application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfPlacement() {
        return dateOfPlacement;
    }

    public DeterminedChild dateOfPlacement(LocalDate dateOfPlacement) {
        this.dateOfPlacement = dateOfPlacement;
        return this;
    }

    public void setDateOfPlacement(LocalDate dateOfPlacement) {
        this.dateOfPlacement = dateOfPlacement;
    }

    public Person getPerson() {
        return person;
    }

    public DeterminedChild person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public CountyType getCountyOfJurisdiction() {
        return countyOfJurisdiction;
    }

    public DeterminedChild countyOfJurisdiction(CountyType countyType) {
        this.countyOfJurisdiction = countyType;
        return this;
    }

    public void setCountyOfJurisdiction(CountyType countyType) {
        this.countyOfJurisdiction = countyType;
    }

    public Application getApplication() {
        return application;
    }

    public DeterminedChild application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeterminedChild determinedChild = (DeterminedChild) o;
        if (determinedChild.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), determinedChild.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeterminedChild{" +
            "id=" + getId() +
            ", dateOfPlacement='" + getDateOfPlacement() + "'" +
            "}";
    }
}
