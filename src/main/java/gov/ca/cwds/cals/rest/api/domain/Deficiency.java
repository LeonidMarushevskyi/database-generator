package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Deficiency.
 */
@Entity
@Table(name = "deficiency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Deficiency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "deficiency_type")
    private String deficiencyType;

    @Column(name = "deficiency_type_description")
    private String deficiencyTypeDescription;

    @Column(name = "poc_date")
    private ZonedDateTime pocDate;

    @Column(name = "fac_section_violated")
    private String facSectionViolated;

    @Column(name = "deficiency")
    private String deficiency;

    @Column(name = "correction_plan")
    private String correctionPlan;

    @ManyToOne
    private Inspection inspection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeficiencyType() {
        return deficiencyType;
    }

    public Deficiency deficiencyType(String deficiencyType) {
        this.deficiencyType = deficiencyType;
        return this;
    }

    public void setDeficiencyType(String deficiencyType) {
        this.deficiencyType = deficiencyType;
    }

    public String getDeficiencyTypeDescription() {
        return deficiencyTypeDescription;
    }

    public Deficiency deficiencyTypeDescription(String deficiencyTypeDescription) {
        this.deficiencyTypeDescription = deficiencyTypeDescription;
        return this;
    }

    public void setDeficiencyTypeDescription(String deficiencyTypeDescription) {
        this.deficiencyTypeDescription = deficiencyTypeDescription;
    }

    public ZonedDateTime getPocDate() {
        return pocDate;
    }

    public Deficiency pocDate(ZonedDateTime pocDate) {
        this.pocDate = pocDate;
        return this;
    }

    public void setPocDate(ZonedDateTime pocDate) {
        this.pocDate = pocDate;
    }

    public String getFacSectionViolated() {
        return facSectionViolated;
    }

    public Deficiency facSectionViolated(String facSectionViolated) {
        this.facSectionViolated = facSectionViolated;
        return this;
    }

    public void setFacSectionViolated(String facSectionViolated) {
        this.facSectionViolated = facSectionViolated;
    }

    public String getDeficiency() {
        return deficiency;
    }

    public Deficiency deficiency(String deficiency) {
        this.deficiency = deficiency;
        return this;
    }

    public void setDeficiency(String deficiency) {
        this.deficiency = deficiency;
    }

    public String getCorrectionPlan() {
        return correctionPlan;
    }

    public Deficiency correctionPlan(String correctionPlan) {
        this.correctionPlan = correctionPlan;
        return this;
    }

    public void setCorrectionPlan(String correctionPlan) {
        this.correctionPlan = correctionPlan;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public Deficiency inspection(Inspection inspection) {
        this.inspection = inspection;
        return this;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deficiency deficiency = (Deficiency) o;
        if (deficiency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deficiency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Deficiency{" +
            "id=" + getId() +
            ", deficiencyType='" + getDeficiencyType() + "'" +
            ", deficiencyTypeDescription='" + getDeficiencyTypeDescription() + "'" +
            ", pocDate='" + getPocDate() + "'" +
            ", facSectionViolated='" + getFacSectionViolated() + "'" +
            ", deficiency='" + getDeficiency() + "'" +
            ", correctionPlan='" + getCorrectionPlan() + "'" +
            "}";
    }
}
