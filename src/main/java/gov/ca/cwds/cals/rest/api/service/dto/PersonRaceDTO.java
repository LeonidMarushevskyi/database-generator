package gov.ca.cwds.cals.rest.api.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonRace entity.
 */
public class PersonRaceDTO implements Serializable {

    private Long id;

    private Long personId;

    private Long raceId;

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

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceTypeId) {
        this.raceId = raceTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonRaceDTO personRaceDTO = (PersonRaceDTO) o;
        if(personRaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personRaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonRaceDTO{" +
            "id=" + getId() +
            "}";
    }
}
