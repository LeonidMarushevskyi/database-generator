package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FacilityAddress.
 */
@Entity
@Table(name = "facility_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacilityAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Facility facility;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @ManyToOne
    private AddressType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityAddress facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Address getAddress() {
        return address;
    }

    public FacilityAddress address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AddressType getType() {
        return type;
    }

    public FacilityAddress type(AddressType addressType) {
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
        FacilityAddress facilityAddress = (FacilityAddress) o;
        if (facilityAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facilityAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityAddress{" +
            "id=" + id +
            '}';
    }
}
