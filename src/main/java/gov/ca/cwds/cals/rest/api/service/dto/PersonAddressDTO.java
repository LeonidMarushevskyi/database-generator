package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonAddress entity.
 */
public class PersonAddressDTO implements Serializable {

    private Long id;

    private Long personId;

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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long addressTypeId) {
        this.typeId = addressTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonAddressDTO personAddressDTO = (PersonAddressDTO) o;

        if ( ! Objects.equals(id, personAddressDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonAddressDTO{" +
            "id=" + id +
            '}';
    }
}
