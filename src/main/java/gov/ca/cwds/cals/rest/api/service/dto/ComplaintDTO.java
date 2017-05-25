package gov.ca.cwds.cals.rest.api.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Complaint entity.
 */
public class ComplaintDTO implements Serializable {

    private Long id;

    private LocalDate complaintDate;

    private String controlNumber;

    private Integer priorityLevel;

    private String status;

    private LocalDate approvalDate;

    private Long facilityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
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

        ComplaintDTO complaintDTO = (ComplaintDTO) o;
        if(complaintDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), complaintDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ComplaintDTO{" +
            "id=" + getId() +
            ", complaintDate='" + getComplaintDate() + "'" +
            ", controlNumber='" + getControlNumber() + "'" +
            ", priorityLevel='" + getPriorityLevel() + "'" +
            ", status='" + getStatus() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            "}";
    }
}
