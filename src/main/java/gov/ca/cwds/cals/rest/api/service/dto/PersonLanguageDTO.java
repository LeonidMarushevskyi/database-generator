package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonLanguage entity.
 */
public class PersonLanguageDTO implements Serializable {

    private Long id;

    private Long personId;

    private Long languageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageTypeId) {
        this.languageId = languageTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonLanguageDTO personLanguageDTO = (PersonLanguageDTO) o;
        if(personLanguageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personLanguageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonLanguageDTO{" +
            "id=" + getId() +
            "}";
    }
}
