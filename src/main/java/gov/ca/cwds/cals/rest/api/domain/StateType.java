package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StateType.
 */
@Entity
@Table(name = "state_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "state")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CountyType> counties = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StateType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CountyType> getCounties() {
        return counties;
    }

    public StateType counties(Set<CountyType> countyTypes) {
        this.counties = countyTypes;
        return this;
    }

    public StateType addCounties(CountyType countyType) {
        this.counties.add(countyType);
        countyType.setState(this);
        return this;
    }

    public StateType removeCounties(CountyType countyType) {
        this.counties.remove(countyType);
        countyType.setState(null);
        return this;
    }

    public void setCounties(Set<CountyType> countyTypes) {
        this.counties = countyTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StateType stateType = (StateType) o;
        if (stateType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stateType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StateType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
