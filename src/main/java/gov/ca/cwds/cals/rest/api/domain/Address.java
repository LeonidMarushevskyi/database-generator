package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "street_address", length = 100, nullable = false)
    private String streetAddress;

    @NotNull
    @Size(max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "state", length = 2, nullable = false)
    private String state;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

    @Size(min = 4, max = 4)
    @Column(name = "zip_suffix_code", length = 4)
    private String zipSuffixCode;

    @Column(name = "longitude", precision=10, scale=2)
    private BigDecimal longitude;

    @Column(name = "lattitude", precision=10, scale=2)
    private BigDecimal lattitude;

    @Column(name = "deliverable")
    private Boolean deliverable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Address streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipSuffixCode() {
        return zipSuffixCode;
    }

    public Address zipSuffixCode(String zipSuffixCode) {
        this.zipSuffixCode = zipSuffixCode;
        return this;
    }

    public void setZipSuffixCode(String zipSuffixCode) {
        this.zipSuffixCode = zipSuffixCode;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Address longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLattitude() {
        return lattitude;
    }

    public Address lattitude(BigDecimal lattitude) {
        this.lattitude = lattitude;
        return this;
    }

    public void setLattitude(BigDecimal lattitude) {
        this.lattitude = lattitude;
    }

    public Boolean isDeliverable() {
        return deliverable;
    }

    public Address deliverable(Boolean deliverable) {
        this.deliverable = deliverable;
        return this;
    }

    public void setDeliverable(Boolean deliverable) {
        this.deliverable = deliverable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", zipSuffixCode='" + getZipSuffixCode() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", lattitude='" + getLattitude() + "'" +
            ", deliverable='" + isDeliverable() + "'" +
            "}";
    }
}
