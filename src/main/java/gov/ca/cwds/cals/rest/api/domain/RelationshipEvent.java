package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RelationshipEvent.
 */
@Entity
@Table(name = "relationship_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RelationshipEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "event_place")
    private String eventPlace;

    @Column(name = "event_date")
    private LocalDate eventDate;

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

    @ManyToOne
    private RelationshipEventType eventType;

    @OneToOne(mappedBy = "startEvent")
    @JsonIgnore
    private AppRelHistory relationShip;

    @OneToOne(mappedBy = "endEvent")
    @JsonIgnore
    private AppRelHistory relationShip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public RelationshipEvent eventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
        return this;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public RelationshipEvent eventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public RelationshipEvent createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public RelationshipEvent createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public RelationshipEvent updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public RelationshipEvent updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public RelationshipEventType getEventType() {
        return eventType;
    }

    public RelationshipEvent eventType(RelationshipEventType relationshipEventType) {
        this.eventType = relationshipEventType;
        return this;
    }

    public void setEventType(RelationshipEventType relationshipEventType) {
        this.eventType = relationshipEventType;
    }

    public AppRelHistory getRelationShip() {
        return relationShip;
    }

    public RelationshipEvent relationShip(AppRelHistory appRelHistory) {
        this.relationShip = appRelHistory;
        return this;
    }

    public void setRelationShip(AppRelHistory appRelHistory) {
        this.relationShip = appRelHistory;
    }

    public AppRelHistory getRelationShip() {
        return relationShip;
    }

    public RelationshipEvent relationShip(AppRelHistory appRelHistory) {
        this.relationShip = appRelHistory;
        return this;
    }

    public void setRelationShip(AppRelHistory appRelHistory) {
        this.relationShip = appRelHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelationshipEvent relationshipEvent = (RelationshipEvent) o;
        if (relationshipEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relationshipEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RelationshipEvent{" +
            "id=" + getId() +
            ", eventPlace='" + getEventPlace() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
