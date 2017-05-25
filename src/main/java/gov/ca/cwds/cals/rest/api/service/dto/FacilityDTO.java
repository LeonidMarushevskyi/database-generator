package gov.ca.cwds.cals.rest.api.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Facility entity.
 */
public class FacilityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String licenseeName;

    private String licenseeType;

    @NotNull
    private Long licenseNumber;

    private String licenseStatus;

    @NotNull
    private Integer capacity;

    @NotNull
    private LocalDate licenseEffectiveDate;

    @NotNull
    private LocalDate originalApplicationRecievedDate;

    private LocalDate lastVisitDate;

    @Size(max = 50)
    private String emailAddress;

    private String lastVisitReason;

    private Long assignedWorkerId;

    private Long districtOfficeId;

    private Long typeId;

    private Long statusId;

    private Long countyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseeName() {
        return licenseeName;
    }

    public void setLicenseeName(String licenseeName) {
        this.licenseeName = licenseeName;
    }

    public String getLicenseeType() {
        return licenseeType;
    }

    public void setLicenseeType(String licenseeType) {
        this.licenseeType = licenseeType;
    }

    public Long getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDate getLicenseEffectiveDate() {
        return licenseEffectiveDate;
    }

    public void setLicenseEffectiveDate(LocalDate licenseEffectiveDate) {
        this.licenseEffectiveDate = licenseEffectiveDate;
    }

    public LocalDate getOriginalApplicationRecievedDate() {
        return originalApplicationRecievedDate;
    }

    public void setOriginalApplicationRecievedDate(LocalDate originalApplicationRecievedDate) {
        this.originalApplicationRecievedDate = originalApplicationRecievedDate;
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastVisitReason() {
        return lastVisitReason;
    }

    public void setLastVisitReason(String lastVisitReason) {
        this.lastVisitReason = lastVisitReason;
    }

    public Long getAssignedWorkerId() {
        return assignedWorkerId;
    }

    public void setAssignedWorkerId(Long assignedWorkerId) {
        this.assignedWorkerId = assignedWorkerId;
    }

    public Long getDistrictOfficeId() {
        return districtOfficeId;
    }

    public void setDistrictOfficeId(Long districtOfficeId) {
        this.districtOfficeId = districtOfficeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long facilityTypeId) {
        this.typeId = facilityTypeId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long facilityStatusId) {
        this.statusId = facilityStatusId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacilityDTO facilityDTO = (FacilityDTO) o;
        if(facilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacilityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", licenseeName='" + getLicenseeName() + "'" +
            ", licenseeType='" + getLicenseeType() + "'" +
            ", licenseNumber='" + getLicenseNumber() + "'" +
            ", licenseStatus='" + getLicenseStatus() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", licenseEffectiveDate='" + getLicenseEffectiveDate() + "'" +
            ", originalApplicationRecievedDate='" + getOriginalApplicationRecievedDate() + "'" +
            ", lastVisitDate='" + getLastVisitDate() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", lastVisitReason='" + getLastVisitReason() + "'" +
            "}";
    }
}
