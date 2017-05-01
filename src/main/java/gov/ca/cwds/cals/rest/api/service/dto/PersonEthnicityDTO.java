package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonEthnicity entity.
 */
public class PersonEthnicityDTO implements Serializable {

    private Long id;

    private Long ethnicityId;

    private Long subEthnicityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEthnicityId() {
        return ethnicityId;
    }

    public void setEthnicityId(Long ethnicityTypeId) {
        this.ethnicityId = ethnicityTypeId;
    }

    public Long getSubEthnicityId() {
        return subEthnicityId;
    }

    public void setSubEthnicityId(Long ethnicityTypeId) {
        this.subEthnicityId = ethnicityTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonEthnicityDTO personEthnicityDTO = (PersonEthnicityDTO) o;

        if ( ! Objects.equals(id, personEthnicityDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonEthnicityDTO{" +
            "id=" + id +
            '}';
    }
}
