package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "ssn")
    private String ssn;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "drivers_license_number", nullable = false)
    private String driversLicenseNumber;

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

    @ManyToOne
    private Application application;

    @ManyToOne
    private Household household;

    @OneToOne
    @JoinColumn(unique = true)
    private EducationLevelType educationHighestLevel;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonPreviousName> previousNames = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmailAddress> emailAddresses = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonPhone> phoneNumbers = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employment> employments = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonAddress> addresses = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EducationPoint> educationPoints = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private GenderType gender;

    @ManyToOne(optional = false)
    @NotNull
    private RaceType race;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "person_ethnicities",
               joinColumns = @JoinColumn(name="people_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ethnicities_id", referencedColumnName="id"))
    private Set<EthnicityType> ethnicities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Person middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSsn() {
        return ssn;
    }

    public Person ssn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Person dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public Person driversLicenseNumber(String driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
        return this;
    }

    public void setDriversLicenseNumber(String driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public Person createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public Person createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public Person updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public Person updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Application getApplication() {
        return application;
    }

    public Person application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Household getHousehold() {
        return household;
    }

    public Person household(Household household) {
        this.household = household;
        return this;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public EducationLevelType getEducationHighestLevel() {
        return educationHighestLevel;
    }

    public Person educationHighestLevel(EducationLevelType educationLevelType) {
        this.educationHighestLevel = educationLevelType;
        return this;
    }

    public void setEducationHighestLevel(EducationLevelType educationLevelType) {
        this.educationHighestLevel = educationLevelType;
    }

    public Set<PersonPreviousName> getPreviousNames() {
        return previousNames;
    }

    public Person previousNames(Set<PersonPreviousName> personPreviousNames) {
        this.previousNames = personPreviousNames;
        return this;
    }

    public Person addPreviousNames(PersonPreviousName personPreviousName) {
        this.previousNames.add(personPreviousName);
        personPreviousName.setPerson(this);
        return this;
    }

    public Person removePreviousNames(PersonPreviousName personPreviousName) {
        this.previousNames.remove(personPreviousName);
        personPreviousName.setPerson(null);
        return this;
    }

    public void setPreviousNames(Set<PersonPreviousName> personPreviousNames) {
        this.previousNames = personPreviousNames;
    }

    public Set<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    public Person emailAddresses(Set<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
        return this;
    }

    public Person addEmailAddresses(EmailAddress emailAddress) {
        this.emailAddresses.add(emailAddress);
        emailAddress.setPerson(this);
        return this;
    }

    public Person removeEmailAddresses(EmailAddress emailAddress) {
        this.emailAddresses.remove(emailAddress);
        emailAddress.setPerson(null);
        return this;
    }

    public void setEmailAddresses(Set<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public Set<PersonPhone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Person phoneNumbers(Set<PersonPhone> personPhones) {
        this.phoneNumbers = personPhones;
        return this;
    }

    public Person addPhoneNumbers(PersonPhone personPhone) {
        this.phoneNumbers.add(personPhone);
        personPhone.setPerson(this);
        return this;
    }

    public Person removePhoneNumbers(PersonPhone personPhone) {
        this.phoneNumbers.remove(personPhone);
        personPhone.setPerson(null);
        return this;
    }

    public void setPhoneNumbers(Set<PersonPhone> personPhones) {
        this.phoneNumbers = personPhones;
    }

    public Set<Employment> getEmployments() {
        return employments;
    }

    public Person employments(Set<Employment> employments) {
        this.employments = employments;
        return this;
    }

    public Person addEmployments(Employment employment) {
        this.employments.add(employment);
        employment.setPerson(this);
        return this;
    }

    public Person removeEmployments(Employment employment) {
        this.employments.remove(employment);
        employment.setPerson(null);
        return this;
    }

    public void setEmployments(Set<Employment> employments) {
        this.employments = employments;
    }

    public Set<PersonAddress> getAddresses() {
        return addresses;
    }

    public Person addresses(Set<PersonAddress> personAddresses) {
        this.addresses = personAddresses;
        return this;
    }

    public Person addAddresses(PersonAddress personAddress) {
        this.addresses.add(personAddress);
        personAddress.setPerson(this);
        return this;
    }

    public Person removeAddresses(PersonAddress personAddress) {
        this.addresses.remove(personAddress);
        personAddress.setPerson(null);
        return this;
    }

    public void setAddresses(Set<PersonAddress> personAddresses) {
        this.addresses = personAddresses;
    }

    public Set<EducationPoint> getEducationPoints() {
        return educationPoints;
    }

    public Person educationPoints(Set<EducationPoint> educationPoints) {
        this.educationPoints = educationPoints;
        return this;
    }

    public Person addEducationPoints(EducationPoint educationPoint) {
        this.educationPoints.add(educationPoint);
        educationPoint.setPerson(this);
        return this;
    }

    public Person removeEducationPoints(EducationPoint educationPoint) {
        this.educationPoints.remove(educationPoint);
        educationPoint.setPerson(null);
        return this;
    }

    public void setEducationPoints(Set<EducationPoint> educationPoints) {
        this.educationPoints = educationPoints;
    }

    public GenderType getGender() {
        return gender;
    }

    public Person gender(GenderType genderType) {
        this.gender = genderType;
        return this;
    }

    public void setGender(GenderType genderType) {
        this.gender = genderType;
    }

    public RaceType getRace() {
        return race;
    }

    public Person race(RaceType raceType) {
        this.race = raceType;
        return this;
    }

    public void setRace(RaceType raceType) {
        this.race = raceType;
    }

    public Set<EthnicityType> getEthnicities() {
        return ethnicities;
    }

    public Person ethnicities(Set<EthnicityType> ethnicityTypes) {
        this.ethnicities = ethnicityTypes;
        return this;
    }

    public Person addEthnicities(EthnicityType ethnicityType) {
        this.ethnicities.add(ethnicityType);
        return this;
    }

    public Person removeEthnicities(EthnicityType ethnicityType) {
        this.ethnicities.remove(ethnicityType);
        return this;
    }

    public void setEthnicities(Set<EthnicityType> ethnicityTypes) {
        this.ethnicities = ethnicityTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", ssn='" + getSsn() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", driversLicenseNumber='" + getDriversLicenseNumber() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
