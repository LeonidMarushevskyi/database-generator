package gov.ca.cwds.cals.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LicensureHistory.
 */
@Entity
@Table(name = "licensure_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LicensureHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "licensure_history_question_1", nullable = false)
    private Boolean licensureHistoryQuestion1;

    @Column(name = "licensure_history_question_11")
    private String licensureHistoryQuestion11;

    @Column(name = "licensure_history_question_12")
    private String licensureHistoryQuestion12;

    @NotNull
    @Column(name = "licensure_history_question_2", nullable = false)
    private Boolean licensureHistoryQuestion2;

    @Column(name = "licensure_history_question_21")
    private String licensureHistoryQuestion21;

    @NotNull
    @Column(name = "licensure_history_question_3", nullable = false)
    private Boolean licensureHistoryQuestion3;

    @Column(name = "licensure_history_question_31")
    private String licensureHistoryQuestion31;

    @NotNull
    @Column(name = "licensure_history_question_4", nullable = false)
    private Boolean licensureHistoryQuestion4;

    @Column(name = "licensure_history_question_41")
    private String licensureHistoryQuestion41;

    @NotNull
    @Column(name = "licensure_history_question_5", nullable = false)
    private Boolean licensureHistoryQuestion5;

    @Column(name = "licensure_history_question_51")
    private String licensureHistoryQuestion51;

    @NotNull
    @Column(name = "licensure_history_question_6", nullable = false)
    private Boolean licensureHistoryQuestion6;

    @Column(name = "licensure_history_question_61")
    private String licensureHistoryQuestion61;

    @NotNull
    @Column(name = "licensure_history_question_7", nullable = false)
    private Boolean licensureHistoryQuestion7;

    @Column(name = "licensure_history_question_71")
    private String licensureHistoryQuestion71;

    @OneToOne(mappedBy = "licensureHistory")
    @JsonIgnore
    private Application application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isLicensureHistoryQuestion1() {
        return licensureHistoryQuestion1;
    }

    public LicensureHistory licensureHistoryQuestion1(Boolean licensureHistoryQuestion1) {
        this.licensureHistoryQuestion1 = licensureHistoryQuestion1;
        return this;
    }

    public void setLicensureHistoryQuestion1(Boolean licensureHistoryQuestion1) {
        this.licensureHistoryQuestion1 = licensureHistoryQuestion1;
    }

    public String getLicensureHistoryQuestion11() {
        return licensureHistoryQuestion11;
    }

    public LicensureHistory licensureHistoryQuestion11(String licensureHistoryQuestion11) {
        this.licensureHistoryQuestion11 = licensureHistoryQuestion11;
        return this;
    }

    public void setLicensureHistoryQuestion11(String licensureHistoryQuestion11) {
        this.licensureHistoryQuestion11 = licensureHistoryQuestion11;
    }

    public String getLicensureHistoryQuestion12() {
        return licensureHistoryQuestion12;
    }

    public LicensureHistory licensureHistoryQuestion12(String licensureHistoryQuestion12) {
        this.licensureHistoryQuestion12 = licensureHistoryQuestion12;
        return this;
    }

    public void setLicensureHistoryQuestion12(String licensureHistoryQuestion12) {
        this.licensureHistoryQuestion12 = licensureHistoryQuestion12;
    }

    public Boolean isLicensureHistoryQuestion2() {
        return licensureHistoryQuestion2;
    }

    public LicensureHistory licensureHistoryQuestion2(Boolean licensureHistoryQuestion2) {
        this.licensureHistoryQuestion2 = licensureHistoryQuestion2;
        return this;
    }

    public void setLicensureHistoryQuestion2(Boolean licensureHistoryQuestion2) {
        this.licensureHistoryQuestion2 = licensureHistoryQuestion2;
    }

    public String getLicensureHistoryQuestion21() {
        return licensureHistoryQuestion21;
    }

    public LicensureHistory licensureHistoryQuestion21(String licensureHistoryQuestion21) {
        this.licensureHistoryQuestion21 = licensureHistoryQuestion21;
        return this;
    }

    public void setLicensureHistoryQuestion21(String licensureHistoryQuestion21) {
        this.licensureHistoryQuestion21 = licensureHistoryQuestion21;
    }

    public Boolean isLicensureHistoryQuestion3() {
        return licensureHistoryQuestion3;
    }

    public LicensureHistory licensureHistoryQuestion3(Boolean licensureHistoryQuestion3) {
        this.licensureHistoryQuestion3 = licensureHistoryQuestion3;
        return this;
    }

    public void setLicensureHistoryQuestion3(Boolean licensureHistoryQuestion3) {
        this.licensureHistoryQuestion3 = licensureHistoryQuestion3;
    }

    public String getLicensureHistoryQuestion31() {
        return licensureHistoryQuestion31;
    }

    public LicensureHistory licensureHistoryQuestion31(String licensureHistoryQuestion31) {
        this.licensureHistoryQuestion31 = licensureHistoryQuestion31;
        return this;
    }

    public void setLicensureHistoryQuestion31(String licensureHistoryQuestion31) {
        this.licensureHistoryQuestion31 = licensureHistoryQuestion31;
    }

    public Boolean isLicensureHistoryQuestion4() {
        return licensureHistoryQuestion4;
    }

    public LicensureHistory licensureHistoryQuestion4(Boolean licensureHistoryQuestion4) {
        this.licensureHistoryQuestion4 = licensureHistoryQuestion4;
        return this;
    }

    public void setLicensureHistoryQuestion4(Boolean licensureHistoryQuestion4) {
        this.licensureHistoryQuestion4 = licensureHistoryQuestion4;
    }

    public String getLicensureHistoryQuestion41() {
        return licensureHistoryQuestion41;
    }

    public LicensureHistory licensureHistoryQuestion41(String licensureHistoryQuestion41) {
        this.licensureHistoryQuestion41 = licensureHistoryQuestion41;
        return this;
    }

    public void setLicensureHistoryQuestion41(String licensureHistoryQuestion41) {
        this.licensureHistoryQuestion41 = licensureHistoryQuestion41;
    }

    public Boolean isLicensureHistoryQuestion5() {
        return licensureHistoryQuestion5;
    }

    public LicensureHistory licensureHistoryQuestion5(Boolean licensureHistoryQuestion5) {
        this.licensureHistoryQuestion5 = licensureHistoryQuestion5;
        return this;
    }

    public void setLicensureHistoryQuestion5(Boolean licensureHistoryQuestion5) {
        this.licensureHistoryQuestion5 = licensureHistoryQuestion5;
    }

    public String getLicensureHistoryQuestion51() {
        return licensureHistoryQuestion51;
    }

    public LicensureHistory licensureHistoryQuestion51(String licensureHistoryQuestion51) {
        this.licensureHistoryQuestion51 = licensureHistoryQuestion51;
        return this;
    }

    public void setLicensureHistoryQuestion51(String licensureHistoryQuestion51) {
        this.licensureHistoryQuestion51 = licensureHistoryQuestion51;
    }

    public Boolean isLicensureHistoryQuestion6() {
        return licensureHistoryQuestion6;
    }

    public LicensureHistory licensureHistoryQuestion6(Boolean licensureHistoryQuestion6) {
        this.licensureHistoryQuestion6 = licensureHistoryQuestion6;
        return this;
    }

    public void setLicensureHistoryQuestion6(Boolean licensureHistoryQuestion6) {
        this.licensureHistoryQuestion6 = licensureHistoryQuestion6;
    }

    public String getLicensureHistoryQuestion61() {
        return licensureHistoryQuestion61;
    }

    public LicensureHistory licensureHistoryQuestion61(String licensureHistoryQuestion61) {
        this.licensureHistoryQuestion61 = licensureHistoryQuestion61;
        return this;
    }

    public void setLicensureHistoryQuestion61(String licensureHistoryQuestion61) {
        this.licensureHistoryQuestion61 = licensureHistoryQuestion61;
    }

    public Boolean isLicensureHistoryQuestion7() {
        return licensureHistoryQuestion7;
    }

    public LicensureHistory licensureHistoryQuestion7(Boolean licensureHistoryQuestion7) {
        this.licensureHistoryQuestion7 = licensureHistoryQuestion7;
        return this;
    }

    public void setLicensureHistoryQuestion7(Boolean licensureHistoryQuestion7) {
        this.licensureHistoryQuestion7 = licensureHistoryQuestion7;
    }

    public String getLicensureHistoryQuestion71() {
        return licensureHistoryQuestion71;
    }

    public LicensureHistory licensureHistoryQuestion71(String licensureHistoryQuestion71) {
        this.licensureHistoryQuestion71 = licensureHistoryQuestion71;
        return this;
    }

    public void setLicensureHistoryQuestion71(String licensureHistoryQuestion71) {
        this.licensureHistoryQuestion71 = licensureHistoryQuestion71;
    }

    public Application getApplication() {
        return application;
    }

    public LicensureHistory application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LicensureHistory licensureHistory = (LicensureHistory) o;
        if (licensureHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licensureHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LicensureHistory{" +
            "id=" + getId() +
            ", licensureHistoryQuestion1='" + isLicensureHistoryQuestion1() + "'" +
            ", licensureHistoryQuestion11='" + getLicensureHistoryQuestion11() + "'" +
            ", licensureHistoryQuestion12='" + getLicensureHistoryQuestion12() + "'" +
            ", licensureHistoryQuestion2='" + isLicensureHistoryQuestion2() + "'" +
            ", licensureHistoryQuestion21='" + getLicensureHistoryQuestion21() + "'" +
            ", licensureHistoryQuestion3='" + isLicensureHistoryQuestion3() + "'" +
            ", licensureHistoryQuestion31='" + getLicensureHistoryQuestion31() + "'" +
            ", licensureHistoryQuestion4='" + isLicensureHistoryQuestion4() + "'" +
            ", licensureHistoryQuestion41='" + getLicensureHistoryQuestion41() + "'" +
            ", licensureHistoryQuestion5='" + isLicensureHistoryQuestion5() + "'" +
            ", licensureHistoryQuestion51='" + getLicensureHistoryQuestion51() + "'" +
            ", licensureHistoryQuestion6='" + isLicensureHistoryQuestion6() + "'" +
            ", licensureHistoryQuestion61='" + getLicensureHistoryQuestion61() + "'" +
            ", licensureHistoryQuestion7='" + isLicensureHistoryQuestion7() + "'" +
            ", licensureHistoryQuestion71='" + getLicensureHistoryQuestion71() + "'" +
            "}";
    }
}
