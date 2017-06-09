package gov.ca.cwds.cals.rest.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AppRelHistory.
 */
@Entity
@Table(name = "app_rel_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppRelHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "create_user_id", length = 50, nullable = false)
    private String createUserId;

    @NotNull
    @Column(name = "create_date_time", nullable = false)
    private ZonedDateTime createDateTime;

    @NotNull
    @Size(max = 50)
    @Column(name = "update_user_id", length = 50, nullable = false)
    private String updateUserId;

    @NotNull
    @Column(name = "update_date_time", nullable = false)
    private ZonedDateTime updateDateTime;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private RelationshipEvent startEvent;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private RelationshipEvent endEvent;

    @ManyToOne
    private RelationshipType relationshipType;

    @ManyToOne
    private Applicant applicant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public AppRelHistory createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public AppRelHistory createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public AppRelHistory updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public AppRelHistory updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public RelationshipEvent getStartEvent() {
        return startEvent;
    }

    public AppRelHistory startEvent(RelationshipEvent relationshipEvent) {
        this.startEvent = relationshipEvent;
        return this;
    }

    public void setStartEvent(RelationshipEvent relationshipEvent) {
        this.startEvent = relationshipEvent;
    }

    public RelationshipEvent getEndEvent() {
        return endEvent;
    }

    public AppRelHistory endEvent(RelationshipEvent relationshipEvent) {
        this.endEvent = relationshipEvent;
        return this;
    }

    public void setEndEvent(RelationshipEvent relationshipEvent) {
        this.endEvent = relationshipEvent;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public AppRelHistory relationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
        return this;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public AppRelHistory applicant(Applicant applicant) {
        this.applicant = applicant;
        return this;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppRelHistory appRelHistory = (AppRelHistory) o;
        if (appRelHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appRelHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppRelHistory{" +
            "id=" + getId() +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
