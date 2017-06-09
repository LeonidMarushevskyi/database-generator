package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A HouseholdChild.
 */
@Entity
@Table(name = "household_child")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HouseholdChild implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "is_financially_supported", nullable = false)
    private Boolean isFinanciallySupported;

    @NotNull
    @Column(name = "is_adopted", nullable = false)
    private Boolean isAdopted;

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
    private Person person;

    @ManyToOne(optional = false)
    @NotNull
    private Household household;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsFinanciallySupported() {
        return isFinanciallySupported;
    }

    public HouseholdChild isFinanciallySupported(Boolean isFinanciallySupported) {
        this.isFinanciallySupported = isFinanciallySupported;
        return this;
    }

    public void setIsFinanciallySupported(Boolean isFinanciallySupported) {
        this.isFinanciallySupported = isFinanciallySupported;
    }

    public Boolean isIsAdopted() {
        return isAdopted;
    }

    public HouseholdChild isAdopted(Boolean isAdopted) {
        this.isAdopted = isAdopted;
        return this;
    }

    public void setIsAdopted(Boolean isAdopted) {
        this.isAdopted = isAdopted;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public HouseholdChild createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public HouseholdChild createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public HouseholdChild updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public HouseholdChild updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Person getPerson() {
        return person;
    }

    public HouseholdChild person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Household getHousehold() {
        return household;
    }

    public HouseholdChild household(Household household) {
        this.household = household;
        return this;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HouseholdChild householdChild = (HouseholdChild) o;
        if (householdChild.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), householdChild.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HouseholdChild{" +
            "id=" + getId() +
            ", isFinanciallySupported='" + isIsFinanciallySupported() + "'" +
            ", isAdopted='" + isIsAdopted() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
