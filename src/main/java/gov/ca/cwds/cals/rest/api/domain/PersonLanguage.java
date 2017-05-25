package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonLanguage.
 */
@Entity
@Table(name = "person_language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonLanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private LanguageType language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public PersonLanguage person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public PersonLanguage language(LanguageType languageType) {
        this.language = languageType;
        return this;
    }

    public void setLanguage(LanguageType languageType) {
        this.language = languageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonLanguage personLanguage = (PersonLanguage) o;
        if (personLanguage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personLanguage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonLanguage{" +
            "id=" + getId() +
            "}";
    }
}
