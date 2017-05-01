package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FacilityPhone.
 */
@Entity
@Table(name = "facility_phone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityPhone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Facility facility;

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

    public Facility getFacility() {
        return facility;
    }

    public FacilityPhone facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Phone getPhone() {
        return phone;
    }

    public FacilityPhone phone(Phone phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public PhoneType getType() {
        return type;
    }

    public FacilityPhone type(PhoneType phoneType) {
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
        FacilityPhone facilityPhone = (FacilityPhone) o;
        if (facilityPhone.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityPhone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityPhone{" +
            "id=" + id +
            '}';
    }
}
