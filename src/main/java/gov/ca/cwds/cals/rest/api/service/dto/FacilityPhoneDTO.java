package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FacilityPhone entity.
 */
public class FacilityPhoneDTO implements Serializable {

    private Long id;

    private Long facilityId;

    private Long phoneId;

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

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long phoneTypeId) {
        this.typeId = phoneTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacilityPhoneDTO facilityPhoneDTO = (FacilityPhoneDTO) o;
        if(facilityPhoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facilityPhoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacilityPhoneDTO{" +
            "id=" + getId() +
            "}";
    }
}
