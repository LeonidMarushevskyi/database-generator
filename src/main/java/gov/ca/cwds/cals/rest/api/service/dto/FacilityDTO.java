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
    private String type;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String licenseeName;

    private String licenseeType;

    private String assignedWorker;

    private String districtOffice;

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

    @NotNull
    private String county;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public String getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }
    public String getDistrictOffice() {
        return districtOffice;
    }

    public void setDistrictOffice(String districtOffice) {
        this.districtOffice = districtOffice;
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
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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

        if ( ! Objects.equals(id, facilityDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FacilityDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", name='" + name + "'" +
            ", licenseeName='" + licenseeName + "'" +
            ", licenseeType='" + licenseeType + "'" +
            ", assignedWorker='" + assignedWorker + "'" +
            ", districtOffice='" + districtOffice + "'" +
            ", licenseNumber='" + licenseNumber + "'" +
            ", licenseStatus='" + licenseStatus + "'" +
            ", capacity='" + capacity + "'" +
            ", licenseEffectiveDate='" + licenseEffectiveDate + "'" +
            ", originalApplicationRecievedDate='" + originalApplicationRecievedDate + "'" +
            ", lastVisitDate='" + lastVisitDate + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", lastVisitReason='" + lastVisitReason + "'" +
            ", county='" + county + "'" +
            '}';
    }
}
