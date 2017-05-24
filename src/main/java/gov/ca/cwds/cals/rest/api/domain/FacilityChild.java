package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FacilityChild.
 */
@Entity
@Table(name = "facility_child")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityChild implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_of_placement")
    private LocalDate dateOfPlacement;

    @Column(name = "assigned_worker")
    private String assignedWorker;

    @NotNull
    @Column(name = "county_of_origin", nullable = false)
    private String countyOfOrigin;

    @ManyToOne
    private Facility facility;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfPlacement() {
        return dateOfPlacement;
    }

    public FacilityChild dateOfPlacement(LocalDate dateOfPlacement) {
        this.dateOfPlacement = dateOfPlacement;
        return this;
    }

    public void setDateOfPlacement(LocalDate dateOfPlacement) {
        this.dateOfPlacement = dateOfPlacement;
    }

    public String getAssignedWorker() {
        return assignedWorker;
    }

    public FacilityChild assignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
        return this;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public String getCountyOfOrigin() {
        return countyOfOrigin;
    }

    public FacilityChild countyOfOrigin(String countyOfOrigin) {
        this.countyOfOrigin = countyOfOrigin;
        return this;
    }

    public void setCountyOfOrigin(String countyOfOrigin) {
        this.countyOfOrigin = countyOfOrigin;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityChild facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Person getPerson() {
        return person;
    }

    public FacilityChild person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FacilityChild facilityChild = (FacilityChild) o;
        if (facilityChild.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityChild.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityChild{" +
            "id=" + id +
            ", dateOfPlacement='" + dateOfPlacement + "'" +
            ", assignedWorker='" + assignedWorker + "'" +
            ", countyOfOrigin='" + countyOfOrigin + "'" +
            '}';
    }
}
