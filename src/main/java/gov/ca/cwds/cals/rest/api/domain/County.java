package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A County.
 */
@Entity
@Table(name = "county")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class County implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private Integer code;

    @Size(max = 20)
    @Column(name = "description", length = 20)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public County code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public County description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        County county = (County) o;
        if (county.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, county.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "County{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
