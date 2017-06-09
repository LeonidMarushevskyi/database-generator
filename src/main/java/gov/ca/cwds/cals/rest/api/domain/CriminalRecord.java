package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CriminalRecord.
 */
@Entity
@Table(name = "criminal_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CriminalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Lob
    @Column(name = "offense_description", nullable = false)
    private String offenseDescription;

    @Column(name = "offense_date")
    private LocalDate offenseDate;

    @NotNull
    @Lob
    @Column(name = "offense_explanation", nullable = false)
    private String offenseExplanation;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

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
    private StateType state;

    @ManyToOne
    private HouseholdAdult person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOffenseDescription() {
        return offenseDescription;
    }

    public CriminalRecord offenseDescription(String offenseDescription) {
        this.offenseDescription = offenseDescription;
        return this;
    }

    public void setOffenseDescription(String offenseDescription) {
        this.offenseDescription = offenseDescription;
    }

    public LocalDate getOffenseDate() {
        return offenseDate;
    }

    public CriminalRecord offenseDate(LocalDate offenseDate) {
        this.offenseDate = offenseDate;
        return this;
    }

    public void setOffenseDate(LocalDate offenseDate) {
        this.offenseDate = offenseDate;
    }

    public String getOffenseExplanation() {
        return offenseExplanation;
    }

    public CriminalRecord offenseExplanation(String offenseExplanation) {
        this.offenseExplanation = offenseExplanation;
        return this;
    }

    public void setOffenseExplanation(String offenseExplanation) {
        this.offenseExplanation = offenseExplanation;
    }

    public String getCity() {
        return city;
    }

    public CriminalRecord city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public CriminalRecord createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public CriminalRecord createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public CriminalRecord updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public CriminalRecord updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public StateType getState() {
        return state;
    }

    public CriminalRecord state(StateType stateType) {
        this.state = stateType;
        return this;
    }

    public void setState(StateType stateType) {
        this.state = stateType;
    }

    public HouseholdAdult getPerson() {
        return person;
    }

    public CriminalRecord person(HouseholdAdult householdAdult) {
        this.person = householdAdult;
        return this;
    }

    public void setPerson(HouseholdAdult householdAdult) {
        this.person = householdAdult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CriminalRecord criminalRecord = (CriminalRecord) o;
        if (criminalRecord.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), criminalRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CriminalRecord{" +
            "id=" + getId() +
            ", offenseDescription='" + getOffenseDescription() + "'" +
            ", offenseDate='" + getOffenseDate() + "'" +
            ", offenseExplanation='" + getOffenseExplanation() + "'" +
            ", city='" + getCity() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
