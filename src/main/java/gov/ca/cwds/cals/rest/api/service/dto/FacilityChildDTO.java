package gov.ca.cwds.cals.rest.api.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FacilityChild entity.
 */
public class FacilityChildDTO implements Serializable {

    private Long id;

    private LocalDate dateOfPlacement;

    private String assignedWorker;

    @NotNull
    private String countyOfOrigin;

    private Long facilityId;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDateOfPlacement() {
        return dateOfPlacement;
    }

    public void setDateOfPlacement(LocalDate dateOfPlacement) {
        this.dateOfPlacement = dateOfPlacement;
    }
    public String getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }
    public String getCountyOfOrigin() {
        return countyOfOrigin;
    }

    public void setCountyOfOrigin(String countyOfOrigin) {
        this.countyOfOrigin = countyOfOrigin;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacilityChildDTO facilityChildDTO = (FacilityChildDTO) o;

        if ( ! Objects.equals(id, facilityChildDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityChildDTO{" +
            "id=" + id +
            ", dateOfPlacement='" + dateOfPlacement + "'" +
            ", assignedWorker='" + assignedWorker + "'" +
            ", countyOfOrigin='" + countyOfOrigin + "'" +
            '}';
    }
}
