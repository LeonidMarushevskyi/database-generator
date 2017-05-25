package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FacilityAddress entity.
 */
public class FacilityAddressDTO implements Serializable {

    private Long id;

    private Long facilityId;

    private Long addressId;

    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long addressTypeId) {
        this.typeId = addressTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacilityAddressDTO facilityAddressDTO = (FacilityAddressDTO) o;
        if(facilityAddressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facilityAddressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacilityAddressDTO{" +
            "id=" + getId() +
            "}";
    }
}
