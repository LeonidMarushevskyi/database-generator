package gov.ca.cwds.cals.rest.api.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Phone entity.
 */
public class PhoneDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 16)
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneDTO phoneDTO = (PhoneDTO) o;
        if(phoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), phoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhoneDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            "}";
    }
}
