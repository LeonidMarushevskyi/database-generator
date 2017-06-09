package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CountyType.
 */
@Entity
@Table(name = "county_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CountyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "fips_code", length = 3, nullable = false)
    private String fipsCode;

    @Size(max = 20)
    @Column(name = "description", length = 20)
    private String description;

    @ManyToOne
    private StateType state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public CountyType fipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
        return this;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getDescription() {
        return description;
    }

    public CountyType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StateType getState() {
        return state;
    }

    public CountyType state(StateType stateType) {
        this.state = stateType;
        return this;
    }

    public void setState(StateType stateType) {
        this.state = stateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountyType countyType = (CountyType) o;
        if (countyType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countyType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountyType{" +
            "id=" + getId() +
            ", fipsCode='" + getFipsCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
