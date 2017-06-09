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
 * A HouseholdAdult.
 */
@Entity
@Table(name = "household_adult")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HouseholdAdult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "out_of_state_disclosure_state", nullable = false)
    private Boolean outOfStateDisclosureState;

    @NotNull
    @Column(name = "criminal_record_statement_question_1", nullable = false)
    private Boolean criminalRecordStatementQuestion1;

    @NotNull
    @Column(name = "criminal_record_statement_question_2", nullable = false)
    private Boolean criminalRecordStatementQuestion2;

    @NotNull
    @Column(name = "criminal_record_statement_question_3", nullable = false)
    private Boolean criminalRecordStatementQuestion3;

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

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CriminalRecord> criminalRecords = new HashSet<>();

    @ManyToOne
    private Household household;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "household_adult_other_states",
               joinColumns = @JoinColumn(name="household_adults_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="other_states_id", referencedColumnName="id"))
    private Set<StateType> otherStates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isOutOfStateDisclosureState() {
        return outOfStateDisclosureState;
    }

    public HouseholdAdult outOfStateDisclosureState(Boolean outOfStateDisclosureState) {
        this.outOfStateDisclosureState = outOfStateDisclosureState;
        return this;
    }

    public void setOutOfStateDisclosureState(Boolean outOfStateDisclosureState) {
        this.outOfStateDisclosureState = outOfStateDisclosureState;
    }

    public Boolean isCriminalRecordStatementQuestion1() {
        return criminalRecordStatementQuestion1;
    }

    public HouseholdAdult criminalRecordStatementQuestion1(Boolean criminalRecordStatementQuestion1) {
        this.criminalRecordStatementQuestion1 = criminalRecordStatementQuestion1;
        return this;
    }

    public void setCriminalRecordStatementQuestion1(Boolean criminalRecordStatementQuestion1) {
        this.criminalRecordStatementQuestion1 = criminalRecordStatementQuestion1;
    }

    public Boolean isCriminalRecordStatementQuestion2() {
        return criminalRecordStatementQuestion2;
    }

    public HouseholdAdult criminalRecordStatementQuestion2(Boolean criminalRecordStatementQuestion2) {
        this.criminalRecordStatementQuestion2 = criminalRecordStatementQuestion2;
        return this;
    }

    public void setCriminalRecordStatementQuestion2(Boolean criminalRecordStatementQuestion2) {
        this.criminalRecordStatementQuestion2 = criminalRecordStatementQuestion2;
    }

    public Boolean isCriminalRecordStatementQuestion3() {
        return criminalRecordStatementQuestion3;
    }

    public HouseholdAdult criminalRecordStatementQuestion3(Boolean criminalRecordStatementQuestion3) {
        this.criminalRecordStatementQuestion3 = criminalRecordStatementQuestion3;
        return this;
    }

    public void setCriminalRecordStatementQuestion3(Boolean criminalRecordStatementQuestion3) {
        this.criminalRecordStatementQuestion3 = criminalRecordStatementQuestion3;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public HouseholdAdult createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public HouseholdAdult createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public HouseholdAdult updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public HouseholdAdult updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Person getPerson() {
        return person;
    }

    public HouseholdAdult person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<CriminalRecord> getCriminalRecords() {
        return criminalRecords;
    }

    public HouseholdAdult criminalRecords(Set<CriminalRecord> criminalRecords) {
        this.criminalRecords = criminalRecords;
        return this;
    }

    public HouseholdAdult addCriminalRecords(CriminalRecord criminalRecord) {
        this.criminalRecords.add(criminalRecord);
        criminalRecord.setPerson(this);
        return this;
    }

    public HouseholdAdult removeCriminalRecords(CriminalRecord criminalRecord) {
        this.criminalRecords.remove(criminalRecord);
        criminalRecord.setPerson(null);
        return this;
    }

    public void setCriminalRecords(Set<CriminalRecord> criminalRecords) {
        this.criminalRecords = criminalRecords;
    }

    public Household getHousehold() {
        return household;
    }

    public HouseholdAdult household(Household household) {
        this.household = household;
        return this;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public Set<StateType> getOtherStates() {
        return otherStates;
    }

    public HouseholdAdult otherStates(Set<StateType> stateTypes) {
        this.otherStates = stateTypes;
        return this;
    }

    public HouseholdAdult addOtherStates(StateType stateType) {
        this.otherStates.add(stateType);
        return this;
    }

    public HouseholdAdult removeOtherStates(StateType stateType) {
        this.otherStates.remove(stateType);
        return this;
    }

    public void setOtherStates(Set<StateType> stateTypes) {
        this.otherStates = stateTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HouseholdAdult householdAdult = (HouseholdAdult) o;
        if (householdAdult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), householdAdult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HouseholdAdult{" +
            "id=" + getId() +
            ", outOfStateDisclosureState='" + isOutOfStateDisclosureState() + "'" +
            ", criminalRecordStatementQuestion1='" + isCriminalRecordStatementQuestion1() + "'" +
            ", criminalRecordStatementQuestion2='" + isCriminalRecordStatementQuestion2() + "'" +
            ", criminalRecordStatementQuestion3='" + isCriminalRecordStatementQuestion3() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
