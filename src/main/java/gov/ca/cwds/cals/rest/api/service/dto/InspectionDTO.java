package gov.ca.cwds.cals.rest.api.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Inspection entity.
 */
public class InspectionDTO implements Serializable {

    private Long id;

    private LocalDate representativeSignatureDate;

    private LocalDate form809PrintDate;

    private Long facilityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRepresentativeSignatureDate() {
        return representativeSignatureDate;
    }

    public void setRepresentativeSignatureDate(LocalDate representativeSignatureDate) {
        this.representativeSignatureDate = representativeSignatureDate;
    }

    public LocalDate getForm809PrintDate() {
        return form809PrintDate;
    }

    public void setForm809PrintDate(LocalDate form809PrintDate) {
        this.form809PrintDate = form809PrintDate;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InspectionDTO inspectionDTO = (InspectionDTO) o;
        if(inspectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InspectionDTO{" +
            "id=" + getId() +
            ", representativeSignatureDate='" + getRepresentativeSignatureDate() + "'" +
            ", form809PrintDate='" + getForm809PrintDate() + "'" +
            "}";
    }
}
