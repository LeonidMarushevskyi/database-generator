package gov.ca.cwds.cals.rest.api.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String streetAddress;

    @NotNull
    @Size(max = 50)
    private String city;

    @NotNull
    @Size(min = 2, max = 2)
    private String state;

    @NotNull
    @Size(min = 5, max = 5)
    private String zipCode;

    @Size(min = 4, max = 4)
    private String zipSuffixCode;

    private BigDecimal longitude;

    private BigDecimal lattitude;

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

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipSuffixCode() {
        return zipSuffixCode;
    }

    public void setZipSuffixCode(String zipSuffixCode) {
        this.zipSuffixCode = zipSuffixCode;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLattitude() {
        return lattitude;
    }

    public void setLattitude(BigDecimal lattitude) {
        this.lattitude = lattitude;
    }

    public Boolean isDeliverable() {
        return deliverable;
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

        AddressDTO addressDTO = (AddressDTO) o;
        if(addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
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
