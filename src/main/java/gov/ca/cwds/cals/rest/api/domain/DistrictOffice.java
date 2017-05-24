package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DistrictOffice.
 */
@Entity
@Table(name = "district_office")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DistrictOffice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "facility_number", nullable = false)
    private Long facilityNumber;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilityNumber() {
        return facilityNumber;
    }

    public DistrictOffice facilityNumber(Long facilityNumber) {
        this.facilityNumber = facilityNumber;
        return this;
    }

    public void setFacilityNumber(Long facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public String getName() {
        return name;
    }

    public DistrictOffice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DistrictOffice districtOffice = (DistrictOffice) o;
        if (districtOffice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, districtOffice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DistrictOffice{" +
            "id=" + id +
            ", facilityNumber='" + facilityNumber + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
