package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HouseholdAddress.
 */
@Entity
@Table(name = "household_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HouseholdAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "is_weapons_in_home", nullable = false)
    private Boolean isWeaponsInHome;

    @Size(max = 255)
    @Column(name = "directions_to_home", length = 255)
    private String directionsToHome;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "householdAddress")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BodyOfWater> bodyOfWaters = new HashSet<>();

    @ManyToOne
    private AddressType addressType;

    @ManyToOne
    private PosessionType posessionType;

    @ManyToOne
    private Household address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsWeaponsInHome() {
        return isWeaponsInHome;
    }

    public HouseholdAddress isWeaponsInHome(Boolean isWeaponsInHome) {
        this.isWeaponsInHome = isWeaponsInHome;
        return this;
    }

    public void setIsWeaponsInHome(Boolean isWeaponsInHome) {
        this.isWeaponsInHome = isWeaponsInHome;
    }

    public String getDirectionsToHome() {
        return directionsToHome;
    }

    public HouseholdAddress directionsToHome(String directionsToHome) {
        this.directionsToHome = directionsToHome;
        return this;
    }

    public void setDirectionsToHome(String directionsToHome) {
        this.directionsToHome = directionsToHome;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public HouseholdAddress createUserId(String createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public HouseholdAddress createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public HouseholdAddress updateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        return this;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public HouseholdAddress updateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Address getAddress() {
        return address;
    }

    public HouseholdAddress address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<BodyOfWater> getBodyOfWaters() {
        return bodyOfWaters;
    }

    public HouseholdAddress bodyOfWaters(Set<BodyOfWater> bodyOfWaters) {
        this.bodyOfWaters = bodyOfWaters;
        return this;
    }

    public HouseholdAddress addBodyOfWaters(BodyOfWater bodyOfWater) {
        this.bodyOfWaters.add(bodyOfWater);
        bodyOfWater.setHouseholdAddress(this);
        return this;
    }

    public HouseholdAddress removeBodyOfWaters(BodyOfWater bodyOfWater) {
        this.bodyOfWaters.remove(bodyOfWater);
        bodyOfWater.setHouseholdAddress(null);
        return this;
    }

    public void setBodyOfWaters(Set<BodyOfWater> bodyOfWaters) {
        this.bodyOfWaters = bodyOfWaters;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public HouseholdAddress addressType(AddressType addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public PosessionType getPosessionType() {
        return posessionType;
    }

    public HouseholdAddress posessionType(PosessionType posessionType) {
        this.posessionType = posessionType;
        return this;
    }

    public void setPosessionType(PosessionType posessionType) {
        this.posessionType = posessionType;
    }

    public Household getAddress() {
        return address;
    }

    public HouseholdAddress address(Household household) {
        this.address = household;
        return this;
    }

    public void setAddress(Household household) {
        this.address = household;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HouseholdAddress householdAddress = (HouseholdAddress) o;
        if (householdAddress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), householdAddress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HouseholdAddress{" +
            "id=" + getId() +
            ", isWeaponsInHome='" + isIsWeaponsInHome() + "'" +
            ", directionsToHome='" + getDirectionsToHome() + "'" +
            ", createUserId='" + getCreateUserId() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", updateUserId='" + getUpdateUserId() + "'" +
            ", updateDateTime='" + getUpdateDateTime() + "'" +
            "}";
    }
}
