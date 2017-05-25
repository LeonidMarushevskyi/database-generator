package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonPhone entity.
 */
public class PersonPhoneDTO implements Serializable {

    private Long id;

    private Long personId;

    private Long phoneId;

    private Long typeId;

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

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long phoneTypeId) {
        this.typeId = phoneTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonPhoneDTO personPhoneDTO = (PersonPhoneDTO) o;
        if(personPhoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personPhoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonPhoneDTO{" +
            "id=" + getId() +
            "}";
    }
}
