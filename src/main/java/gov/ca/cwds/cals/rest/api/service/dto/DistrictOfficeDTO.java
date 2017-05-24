package gov.ca.cwds.cals.rest.api.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DistrictOffice entity.
 */
public class DistrictOfficeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long facilityNumber;

    @NotNull
    @Size(max = 50)
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

    public void setFacilityNumber(Long facilityNumber) {
        this.facilityNumber = facilityNumber;
    }
    public String getName() {
        return name;
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

        DistrictOfficeDTO districtOfficeDTO = (DistrictOfficeDTO) o;

        if ( ! Objects.equals(id, districtOfficeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DistrictOfficeDTO{" +
            "id=" + id +
            ", facilityNumber='" + facilityNumber + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
