package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A ChildPreferences.
 */
@Entity
@Table(name = "child_preferences")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChildPreferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "child_preferences_age_group_types",
               joinColumns = @JoinColumn(name="child_preferences_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="age_group_types_id", referencedColumnName="id"))
    private Set<AgeGroupType> ageGroupTypes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "child_preferences_sibling_group_types",
               joinColumns = @JoinColumn(name="child_preferences_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="sibling_group_types_id", referencedColumnName="id"))
    private Set<SiblingGroupType> siblingGroupTypes = new HashSet<>();

    @OneToOne(mappedBy = "")
    @JsonIgnore
    private Application application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public ChildPreferences createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public ChildPreferences createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public ChildPreferences updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public ChildPreferences updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Set<AgeGroupType> getAgeGroupTypes() {
        return ageGroupTypes;
    }

    public ChildPreferences ageGroupTypes(Set<AgeGroupType> ageGroupTypes) {
        this.ageGroupTypes = ageGroupTypes;
        return this;
    }

    public ChildPreferences addAgeGroupTypes(AgeGroupType ageGroupType) {
        this.ageGroupTypes.add(ageGroupType);
        return this;
    }

    public ChildPreferences removeAgeGroupTypes(AgeGroupType ageGroupType) {
        this.ageGroupTypes.remove(ageGroupType);
        return this;
    }

    public void setAgeGroupTypes(Set<AgeGroupType> ageGroupTypes) {
        this.ageGroupTypes = ageGroupTypes;
    }

    public Set<SiblingGroupType> getSiblingGroupTypes() {
        return siblingGroupTypes;
    }

    public ChildPreferences siblingGroupTypes(Set<SiblingGroupType> siblingGroupTypes) {
        this.siblingGroupTypes = siblingGroupTypes;
        return this;
    }

    public ChildPreferences addSiblingGroupTypes(SiblingGroupType siblingGroupType) {
        this.siblingGroupTypes.add(siblingGroupType);
        return this;
    }

    public ChildPreferences removeSiblingGroupTypes(SiblingGroupType siblingGroupType) {
        this.siblingGroupTypes.remove(siblingGroupType);
        return this;
    }

    public void setSiblingGroupTypes(Set<SiblingGroupType> siblingGroupTypes) {
        this.siblingGroupTypes = siblingGroupTypes;
    }

    public Application getApplication() {
        return application;
    }

    public ChildPreferences application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChildPreferences childPreferences = (ChildPreferences) o;
        if (childPreferences.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), childPreferences.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChildPreferences{" +
            "id=" + getId() +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
