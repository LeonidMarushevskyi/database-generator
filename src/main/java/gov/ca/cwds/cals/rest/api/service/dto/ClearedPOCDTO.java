package gov.ca.cwds.cals.rest.api.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ClearedPOC entity.
 */
public class ClearedPOCDTO implements Serializable {

    private Long id;

    private ZonedDateTime pocduedate;

    private String pocsectionviolated;

    private String poccorrectionplan;

    private ZonedDateTime pocdatecleared;

    private String poccomments;

    private Long inspectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPocduedate() {
        return pocduedate;
    }

    public void setPocduedate(ZonedDateTime pocduedate) {
        this.pocduedate = pocduedate;
    }

    public String getPocsectionviolated() {
        return pocsectionviolated;
    }

    public void setPocsectionviolated(String pocsectionviolated) {
        this.pocsectionviolated = pocsectionviolated;
    }

    public String getPoccorrectionplan() {
        return poccorrectionplan;
    }

    public void setPoccorrectionplan(String poccorrectionplan) {
        this.poccorrectionplan = poccorrectionplan;
    }

    public ZonedDateTime getPocdatecleared() {
        return pocdatecleared;
    }

    public void setPocdatecleared(ZonedDateTime pocdatecleared) {
        this.pocdatecleared = pocdatecleared;
    }

    public String getPoccomments() {
        return poccomments;
    }

    public void setPoccomments(String poccomments) {
        this.poccomments = poccomments;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClearedPOCDTO clearedPOCDTO = (ClearedPOCDTO) o;
        if(clearedPOCDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clearedPOCDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClearedPOCDTO{" +
            "id=" + getId() +
            ", pocduedate='" + getPocduedate() + "'" +
            ", pocsectionviolated='" + getPocsectionviolated() + "'" +
            ", poccorrectionplan='" + getPoccorrectionplan() + "'" +
            ", pocdatecleared='" + getPocdatecleared() + "'" +
            ", poccomments='" + getPoccomments() + "'" +
            "}";
    }
}
