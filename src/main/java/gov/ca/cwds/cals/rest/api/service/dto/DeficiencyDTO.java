package gov.ca.cwds.cals.rest.api.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Deficiency entity.
 */
public class DeficiencyDTO implements Serializable {

    private Long id;

    private String deficiencyType;

    private String deficiencyTypeDescription;

    private ZonedDateTime pocDate;

    private String facSectionViolated;

    private String deficiency;

    private String correctionPlan;

    private Long inspectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeficiencyType() {
        return deficiencyType;
    }

    public void setDeficiencyType(String deficiencyType) {
        this.deficiencyType = deficiencyType;
    }

    public String getDeficiencyTypeDescription() {
        return deficiencyTypeDescription;
    }

    public void setDeficiencyTypeDescription(String deficiencyTypeDescription) {
        this.deficiencyTypeDescription = deficiencyTypeDescription;
    }

    public ZonedDateTime getPocDate() {
        return pocDate;
    }

    public void setPocDate(ZonedDateTime pocDate) {
        this.pocDate = pocDate;
    }

    public String getFacSectionViolated() {
        return facSectionViolated;
    }

    public void setFacSectionViolated(String facSectionViolated) {
        this.facSectionViolated = facSectionViolated;
    }

    public String getDeficiency() {
        return deficiency;
    }

    public void setDeficiency(String deficiency) {
        this.deficiency = deficiency;
    }

    public String getCorrectionPlan() {
        return correctionPlan;
    }

    public void setCorrectionPlan(String correctionPlan) {
        this.correctionPlan = correctionPlan;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeficiencyDTO deficiencyDTO = (DeficiencyDTO) o;
        if(deficiencyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deficiencyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeficiencyDTO{" +
            "id=" + getId() +
            ", deficiencyType='" + getDeficiencyType() + "'" +
            ", deficiencyTypeDescription='" + getDeficiencyTypeDescription() + "'" +
            ", pocDate='" + getPocDate() + "'" +
            ", facSectionViolated='" + getFacSectionViolated() + "'" +
            ", deficiency='" + getDeficiency() + "'" +
            ", correctionPlan='" + getCorrectionPlan() + "'" +
            "}";
    }
}
