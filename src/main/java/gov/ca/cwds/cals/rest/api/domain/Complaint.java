package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Complaint.
 */
@Entity
@Table(name = "complaint")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "complaint_date")
    private LocalDate complaintDate;

    @Column(name = "control_number")
    private String controlNumber;

    @Column(name = "priority_level")
    private Integer priorityLevel;

    @Column(name = "status")
    private String status;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @ManyToOne
    private Facility facility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public Complaint complaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
        return this;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public Complaint controlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
        return this;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public Complaint priorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getStatus() {
        return status;
    }

    public Complaint status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public Complaint approvalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
        return this;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Facility getFacility() {
        return facility;
    }

    public Complaint facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Complaint complaint = (Complaint) o;
        if (complaint.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), complaint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Complaint{" +
            "id=" + getId() +
            ", complaintDate='" + getComplaintDate() + "'" +
            ", controlNumber='" + getControlNumber() + "'" +
            ", priorityLevel='" + getPriorityLevel() + "'" +
            ", status='" + getStatus() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            "}";
    }
}
