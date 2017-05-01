package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * @author CALS API team.
 */
@ApiModel(description = "@author CALS API team.")
@Entity
@Table(name = "facility")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(name = "licensee_name", length = 50, nullable = false)
    private String licenseeName;

    @Column(name = "licensee_type")
    private String licenseeType;

    @Column(name = "assigned_worker")
    private String assignedWorker;

    @Column(name = "district_office")
    private String districtOffice;

    @NotNull
    @Column(name = "license_number", nullable = false)
    private Long licenseNumber;

    @Column(name = "license_status")
    private String licenseStatus;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "license_effective_date", nullable = false)
    private LocalDate licenseEffectiveDate;

    @NotNull
    @Column(name = "original_application_recieved_date", nullable = false)
    private LocalDate originalApplicationRecievedDate;

    @Column(name = "last_visit_date")
    private LocalDate lastVisitDate;

    @Size(max = 50)
    @Column(name = "email_address", length = 50)
    private String emailAddress;

    @Column(name = "last_visit_reason")
    private String lastVisitReason;

    @NotNull
    @Column(name = "county", nullable = false)
    private String county;

    @OneToMany(mappedBy = "facility")
//    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FacilityAddress> addresses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Facility type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Facility name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseeName() {
        return licenseeName;
    }

    public Facility licenseeName(String licenseeName) {
        this.licenseeName = licenseeName;
        return this;
    }

    public void setLicenseeName(String licenseeName) {
        this.licenseeName = licenseeName;
    }

    public String getLicenseeType() {
        return licenseeType;
    }

    public Facility licenseeType(String licenseeType) {
        this.licenseeType = licenseeType;
        return this;
    }

    public void setLicenseeType(String licenseeType) {
        this.licenseeType = licenseeType;
    }

    public String getAssignedWorker() {
        return assignedWorker;
    }

    public Facility assignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
        return this;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public String getDistrictOffice() {
        return districtOffice;
    }

    public Facility districtOffice(String districtOffice) {
        this.districtOffice = districtOffice;
        return this;
    }

    public void setDistrictOffice(String districtOffice) {
        this.districtOffice = districtOffice;
    }

    public Long getLicenseNumber() {
        return licenseNumber;
    }

    public Facility licenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
        return this;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public Facility licenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
        return this;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Facility capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDate getLicenseEffectiveDate() {
        return licenseEffectiveDate;
    }

    public Facility licenseEffectiveDate(LocalDate licenseEffectiveDate) {
        this.licenseEffectiveDate = licenseEffectiveDate;
        return this;
    }

    public void setLicenseEffectiveDate(LocalDate licenseEffectiveDate) {
        this.licenseEffectiveDate = licenseEffectiveDate;
    }

    public LocalDate getOriginalApplicationRecievedDate() {
        return originalApplicationRecievedDate;
    }

    public Facility originalApplicationRecievedDate(LocalDate originalApplicationRecievedDate) {
        this.originalApplicationRecievedDate = originalApplicationRecievedDate;
        return this;
    }

    public void setOriginalApplicationRecievedDate(LocalDate originalApplicationRecievedDate) {
        this.originalApplicationRecievedDate = originalApplicationRecievedDate;
    }

    public LocalDate getLastVisitDate() {
        return lastVisitDate;
    }

    public Facility lastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
        return this;
    }

    public void setLastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Facility emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastVisitReason() {
        return lastVisitReason;
    }

    public Facility lastVisitReason(String lastVisitReason) {
        this.lastVisitReason = lastVisitReason;
        return this;
    }

    public void setLastVisitReason(String lastVisitReason) {
        this.lastVisitReason = lastVisitReason;
    }

    public String getCounty() {
        return county;
    }

    public Facility county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Set<FacilityAddress> getAddresses() {
        return addresses;
    }

    public Facility addresses(Set<FacilityAddress> facilityAddresses) {
        this.addresses = facilityAddresses;
        return this;
    }

    public Facility addAddress(FacilityAddress facilityAddress) {
        this.addresses.add(facilityAddress);
        facilityAddress.setFacility(this);
        return this;
    }

    public Facility removeAddress(FacilityAddress facilityAddress) {
        this.addresses.remove(facilityAddress);
        facilityAddress.setFacility(null);
        return this;
    }

    public void setAddresses(Set<FacilityAddress> facilityAddresses) {
        this.addresses = facilityAddresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Facility facility = (Facility) o;
        if (facility.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facility.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Facility{" +
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
