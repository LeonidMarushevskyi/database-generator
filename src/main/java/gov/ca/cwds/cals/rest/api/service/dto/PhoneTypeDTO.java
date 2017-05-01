package gov.ca.cwds.cals.rest.api.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PhoneType entity.
 */
public class PhoneTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 2)
    private String code;

    @Size(max = 20)
    private String type;

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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneTypeDTO phoneTypeDTO = (PhoneTypeDTO) o;

        if ( ! Objects.equals(id, phoneTypeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PhoneTypeDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
