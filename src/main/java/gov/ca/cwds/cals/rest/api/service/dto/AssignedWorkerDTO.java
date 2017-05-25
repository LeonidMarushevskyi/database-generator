package gov.ca.cwds.cals.rest.api.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AssignedWorker entity.
 */
public class AssignedWorkerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 2)
    private String code;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssignedWorkerDTO assignedWorkerDTO = (AssignedWorkerDTO) o;
        if(assignedWorkerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assignedWorkerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssignedWorkerDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
