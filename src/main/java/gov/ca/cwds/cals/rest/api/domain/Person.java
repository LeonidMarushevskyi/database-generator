package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "ssn")
    private String ssn;

    @OneToOne
    @JoinColumn(unique = true)
    private PersonEthnicity ethnicity;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonPhone> phones = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonAddress> addresses = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonLanguage> languages = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonRace> races = new HashSet<>();

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

    public String getGender() {
        return gender;
    }

    public Person gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public Person age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public PersonEthnicity getEthnicity() {
        return ethnicity;
    }

    public Person ethnicity(PersonEthnicity personEthnicity) {
        this.ethnicity = personEthnicity;
        return this;
    }

    public void setEthnicity(PersonEthnicity personEthnicity) {
        this.ethnicity = personEthnicity;
    }

    public Set<PersonPhone> getPhones() {
        return phones;
    }

    public Person phones(Set<PersonPhone> personPhones) {
        this.phones = personPhones;
        return this;
    }

    public Person addPhone(PersonPhone personPhone) {
        this.phones.add(personPhone);
        personPhone.setPerson(this);
        return this;
    }

    public Person removePhone(PersonPhone personPhone) {
        this.phones.remove(personPhone);
        personPhone.setPerson(null);
        return this;
    }

    public void setPhones(Set<PersonPhone> personPhones) {
        this.phones = personPhones;
    }

    public Set<PersonAddress> getAddresses() {
        return addresses;
    }

    public Person addresses(Set<PersonAddress> personAddresses) {
        this.addresses = personAddresses;
        return this;
    }

    public Person addAddress(PersonAddress personAddress) {
        this.addresses.add(personAddress);
        personAddress.setPerson(this);
        return this;
    }

    public Person removeAddress(PersonAddress personAddress) {
        this.addresses.remove(personAddress);
        personAddress.setPerson(null);
        return this;
    }

    public void setAddresses(Set<PersonAddress> personAddresses) {
        this.addresses = personAddresses;
    }

    public Set<PersonLanguage> getLanguages() {
        return languages;
    }

    public Person languages(Set<PersonLanguage> personLanguages) {
        this.languages = personLanguages;
        return this;
    }

    public Person addLanguage(PersonLanguage personLanguage) {
        this.languages.add(personLanguage);
        personLanguage.setPerson(this);
        return this;
    }

    public Person removeLanguage(PersonLanguage personLanguage) {
        this.languages.remove(personLanguage);
        personLanguage.setPerson(null);
        return this;
    }

    public void setLanguages(Set<PersonLanguage> personLanguages) {
        this.languages = personLanguages;
    }

    public Set<PersonRace> getRaces() {
        return races;
    }

    public Person races(Set<PersonRace> personRaces) {
        this.races = personRaces;
        return this;
    }

    public Person addRace(PersonRace personRace) {
        this.races.add(personRace);
        personRace.setPerson(this);
        return this;
    }

    public Person removeRace(PersonRace personRace) {
        this.races.remove(personRace);
        personRace.setPerson(null);
        return this;
    }

    public void setRaces(Set<PersonRace> personRaces) {
        this.races = personRaces;
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
            ", gender='" + getGender() + "'" +
            ", age='" + getAge() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", ssn='" + getSsn() + "'" +
            "}";
    }
}
