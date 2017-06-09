package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * @author CALS API team.
 */
@ApiModel(description = "@author CALS API team.")
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "is_initial_application", nullable = false)
    private Boolean isInitialApplication;

    @Column(name = "other_application_type")
    private String otherApplicationType;

    @Column(name = "is_child_identified")
    private Boolean isChildIdentified;

    @NotNull
    @Column(name = "is_child_currently_in_your_home", nullable = false)
    private Boolean isChildCurrentlyInYourHome;

    @NotNull
    @Size(max = 50)
    @Column(name = "create_user_id", length = 50, nullable = false)
    private String createUserId;

    @NotNull
    @Column(name = "create_date_time", nullable = false)
    private ZonedDateTime createDateTime;

    @NotNull
    @Size(max = 50)
    @Column(name = "update_user_id", length = 50, nullable = false)
    private String updateUserId;

    @NotNull
    @Column(name = "update_date_time", nullable = false)
    private ZonedDateTime updateDateTime;

    @OneToOne
    @JoinColumn(unique = true)
    private LicensureHistory licensureHistory;

    @OneToOne
    @JoinColumn(unique = true)
    private ChildPreferences ;

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> references = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeterminedChild> determinedChildren = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Applicant> applicants = new HashSet<>();

    @ManyToOne
    private CountyType forCountyUseOnly;

    @ManyToOne
    private ApplicationStatusType status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsInitialApplication() {
        return isInitialApplication;
    }

    public Application isInitialApplication(Boolean isInitialApplication) {
        this.isInitialApplication = isInitialApplication;
        return this;
    }

    public void setIsInitialApplication(Boolean isInitialApplication) {
        this.isInitialApplication = isInitialApplication;
    }

    public String getOtherApplicationType() {
        return otherApplicationType;
    }

    public Application otherApplicationType(String otherApplicationType) {
        this.otherApplicationType = otherApplicationType;
        return this;
    }

    public void setOtherApplicationType(String otherApplicationType) {
        this.otherApplicationType = otherApplicationType;
    }

    public Boolean isIsChildIdentified() {
        return isChildIdentified;
    }

    public Application isChildIdentified(Boolean isChildIdentified) {
        this.isChildIdentified = isChildIdentified;
        return this;
    }

    public void setIsChildIdentified(Boolean isChildIdentified) {
        this.isChildIdentified = isChildIdentified;
    }

    public Boolean isIsChildCurrentlyInYourHome() {
        return isChildCurrentlyInYourHome;
    }

    public Application isChildCurrentlyInYourHome(Boolean isChildCurrentlyInYourHome) {
        this.isChildCurrentlyInYourHome = isChildCurrentlyInYourHome;
        return this;
    }

    public void setIsChildCurrentlyInYourHome(Boolean isChildCurrentlyInYourHome) {
        this.isChildCurrentlyInYourHome = isChildCurrentlyInYourHome;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public Application createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public Application createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public Application updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Application updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public LicensureHistory getLicensureHistory() {
        return licensureHistory;
    }

    public Application licensureHistory(LicensureHistory licensureHistory) {
        this.licensureHistory = licensureHistory;
        return this;
    }

    public void setLicensureHistory(LicensureHistory licensureHistory) {
        this.licensureHistory = licensureHistory;
    }

    public ChildPreferences get() {
        return ;
    }

    public Application (ChildPreferences childPreferences) {
        this. = childPreferences;
        return this;
    }

    public void set(ChildPreferences childPreferences) {
        this. = childPreferences;
    }

    public Set<Person> getReferences() {
        return references;
    }

    public Application references(Set<Person> people) {
        this.references = people;
        return this;
    }

    public Application addReferences(Person person) {
        this.references.add(person);
        person.setApplication(this);
        return this;
    }

    public Application removeReferences(Person person) {
        this.references.remove(person);
        person.setApplication(null);
        return this;
    }

    public void setReferences(Set<Person> people) {
        this.references = people;
    }

    public Set<DeterminedChild> getDeterminedChildren() {
        return determinedChildren;
    }

    public Application determinedChildren(Set<DeterminedChild> determinedChildren) {
        this.determinedChildren = determinedChildren;
        return this;
    }

    public Application addDeterminedChild(DeterminedChild determinedChild) {
        this.determinedChildren.add(determinedChild);
        determinedChild.setApplication(this);
        return this;
    }

    public Application removeDeterminedChild(DeterminedChild determinedChild) {
        this.determinedChildren.remove(determinedChild);
        determinedChild.setApplication(null);
        return this;
    }

    public void setDeterminedChildren(Set<DeterminedChild> determinedChildren) {
        this.determinedChildren = determinedChildren;
    }

    public Set<Applicant> getApplicants() {
        return applicants;
    }

    public Application applicants(Set<Applicant> applicants) {
        this.applicants = applicants;
        return this;
    }

    public Application addApplicants(Applicant applicant) {
        this.applicants.add(applicant);
        applicant.setApplication(this);
        return this;
    }

    public Application removeApplicants(Applicant applicant) {
        this.applicants.remove(applicant);
        applicant.setApplication(null);
        return this;
    }

    public void setApplicants(Set<Applicant> applicants) {
        this.applicants = applicants;
    }

    public CountyType getForCountyUseOnly() {
        return forCountyUseOnly;
    }

    public Application forCountyUseOnly(CountyType countyType) {
        this.forCountyUseOnly = countyType;
        return this;
    }

    public void setForCountyUseOnly(CountyType countyType) {
        this.forCountyUseOnly = countyType;
    }

    public ApplicationStatusType getStatus() {
        return status;
    }

    public Application status(ApplicationStatusType applicationStatusType) {
        this.status = applicationStatusType;
        return this;
    }

    public void setStatus(ApplicationStatusType applicationStatusType) {
        this.status = applicationStatusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application application = (Application) o;
        if (application.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), application.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", isInitialApplication='" + isIsInitialApplication() + "'" +
            ", otherApplicationType='" + getOtherApplicationType() + "'" +
            ", isChildIdentified='" + isIsChildIdentified() + "'" +
            ", isChildCurrentlyInYourHome='" + isIsChildCurrentlyInYourHome() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
