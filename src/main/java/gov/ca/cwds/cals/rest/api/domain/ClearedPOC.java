package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ClearedPOC.
 */
@Entity
@Table(name = "cleared_poc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClearedPOC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pocduedate")
    private ZonedDateTime pocduedate;

    @Column(name = "pocsectionviolated")
    private String pocsectionviolated;

    @Column(name = "poccorrectionplan")
    private String poccorrectionplan;

    @Column(name = "pocdatecleared")
    private ZonedDateTime pocdatecleared;

    @Column(name = "poccomments")
    private String poccomments;

    @ManyToOne
    private Inspection inspection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPocduedate() {
        return pocduedate;
    }

    public ClearedPOC pocduedate(ZonedDateTime pocduedate) {
        this.pocduedate = pocduedate;
        return this;
    }

    public void setPocduedate(ZonedDateTime pocduedate) {
        this.pocduedate = pocduedate;
    }

    public String getPocsectionviolated() {
        return pocsectionviolated;
    }

    public ClearedPOC pocsectionviolated(String pocsectionviolated) {
        this.pocsectionviolated = pocsectionviolated;
        return this;
    }

    public void setPocsectionviolated(String pocsectionviolated) {
        this.pocsectionviolated = pocsectionviolated;
    }

    public String getPoccorrectionplan() {
        return poccorrectionplan;
    }

    public ClearedPOC poccorrectionplan(String poccorrectionplan) {
        this.poccorrectionplan = poccorrectionplan;
        return this;
    }

    public void setPoccorrectionplan(String poccorrectionplan) {
        this.poccorrectionplan = poccorrectionplan;
    }

    public ZonedDateTime getPocdatecleared() {
        return pocdatecleared;
    }

    public ClearedPOC pocdatecleared(ZonedDateTime pocdatecleared) {
        this.pocdatecleared = pocdatecleared;
        return this;
    }

    public void setPocdatecleared(ZonedDateTime pocdatecleared) {
        this.pocdatecleared = pocdatecleared;
    }

    public String getPoccomments() {
        return poccomments;
    }

    public ClearedPOC poccomments(String poccomments) {
        this.poccomments = poccomments;
        return this;
    }

    public void setPoccomments(String poccomments) {
        this.poccomments = poccomments;
    }

    public Inspection getInspection() {
        return inspection;
    }

    public ClearedPOC inspection(Inspection inspection) {
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
        ClearedPOC clearedPOC = (ClearedPOC) o;
        if (clearedPOC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clearedPOC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClearedPOC{" +
            "id=" + getId() +
            ", pocduedate='" + getPocduedate() + "'" +
            ", pocsectionviolated='" + getPocsectionviolated() + "'" +
            ", poccorrectionplan='" + getPoccorrectionplan() + "'" +
            ", pocdatecleared='" + getPocdatecleared() + "'" +
            ", poccomments='" + getPoccomments() + "'" +
            "}";
    }
}
