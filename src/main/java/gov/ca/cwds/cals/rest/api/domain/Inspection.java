package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Inspection.
 */
@Entity
@Table(name = "inspection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inspection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "representative_signature_date")
    private LocalDate representativeSignatureDate;

    @Column(name = "form_809_print_date")
    private LocalDate form809PrintDate;

    @ManyToOne
    private Facility facility;

    @OneToMany(mappedBy = "inspection")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deficiency> deficiencies = new HashSet<>();

    @OneToMany(mappedBy = "inspection")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ClearedPOC> clearedPOCS = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRepresentativeSignatureDate() {
        return representativeSignatureDate;
    }

    public Inspection representativeSignatureDate(LocalDate representativeSignatureDate) {
        this.representativeSignatureDate = representativeSignatureDate;
        return this;
    }

    public void setRepresentativeSignatureDate(LocalDate representativeSignatureDate) {
        this.representativeSignatureDate = representativeSignatureDate;
    }

    public LocalDate getForm809PrintDate() {
        return form809PrintDate;
    }

    public Inspection form809PrintDate(LocalDate form809PrintDate) {
        this.form809PrintDate = form809PrintDate;
        return this;
    }

    public void setForm809PrintDate(LocalDate form809PrintDate) {
        this.form809PrintDate = form809PrintDate;
    }

    public Facility getFacility() {
        return facility;
    }

    public Inspection facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Set<Deficiency> getDeficiencies() {
        return deficiencies;
    }

    public Inspection deficiencies(Set<Deficiency> deficiencies) {
        this.deficiencies = deficiencies;
        return this;
    }

    public Inspection addDeficiency(Deficiency deficiency) {
        this.deficiencies.add(deficiency);
        deficiency.setInspection(this);
        return this;
    }

    public Inspection removeDeficiency(Deficiency deficiency) {
        this.deficiencies.remove(deficiency);
        deficiency.setInspection(null);
        return this;
    }

    public void setDeficiencies(Set<Deficiency> deficiencies) {
        this.deficiencies = deficiencies;
    }

    public Set<ClearedPOC> getClearedPOCS() {
        return clearedPOCS;
    }

    public Inspection clearedPOCS(Set<ClearedPOC> clearedPOCS) {
        this.clearedPOCS = clearedPOCS;
        return this;
    }

    public Inspection addClearedPOC(ClearedPOC clearedPOC) {
        this.clearedPOCS.add(clearedPOC);
        clearedPOC.setInspection(this);
        return this;
    }

    public Inspection removeClearedPOC(ClearedPOC clearedPOC) {
        this.clearedPOCS.remove(clearedPOC);
        clearedPOC.setInspection(null);
        return this;
    }

    public void setClearedPOCS(Set<ClearedPOC> clearedPOCS) {
        this.clearedPOCS = clearedPOCS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inspection inspection = (Inspection) o;
        if (inspection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inspection{" +
            "id=" + getId() +
            ", representativeSignatureDate='" + getRepresentativeSignatureDate() + "'" +
            ", form809PrintDate='" + getForm809PrintDate() + "'" +
            "}";
    }
}
