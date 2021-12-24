package com.peopleapps.model;


import com.peopleapps.dto.account.AdmAccountListDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorListDto;
import com.peopleapps.dto.creditCalculator.AdmCCalulatorDto;
import com.peopleapps.dto.creditLine.AdmCreditLineListDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchAccountListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_calculator")
@SequenceGenerator(
        name = "admCreditCalculatorSequence",
        sequenceName = "adm_calculator_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admCreditCalculatorLineListDto",
        classes = @ConstructorResult(
                targetClass = AdmCreditCalculatorListDto.class,
                columns = {
                        @ColumnResult(name = "calculator_id", type = Long.class),
                        @ColumnResult(name = "associate_key", type = UUID.class),
                        @ColumnResult(name = "associate_first_name", type = String.class),
                        @ColumnResult(name = "associate_last_name", type = String.class),
                        @ColumnResult(name = "application_date", type = LocalDateTime.class),
                        @ColumnResult(name = "no_period", type = Long.class),
                        @ColumnResult(name = "no_payments", type = Long.class),
                        @ColumnResult(name = "interest_rate", type = Double.class),
                        @ColumnResult(name = "credit", type = Double.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "interest_final", type = Double.class),
                        @ColumnResult(name = "organization_key", type = UUID.class),
                        @ColumnResult(name = "organization_name", type = String.class),
                        @ColumnResult(name = "organization_commercial", type = String.class),
                        @ColumnResult(name = "credit_line", type = Long.class),
                        @ColumnResult(name = "credit_line_desc", type = String.class),
                        @ColumnResult(name = "membership_number", type = Long.class)
                }
        )
)

@SqlResultSetMapping(
        name = "admCredCalculatorDto",
        classes = @ConstructorResult(
                targetClass = AdmCCalulatorDto.class,
                columns = {
                        @ColumnResult(name = "calculatorId", type = Long.class),
                        @ColumnResult(name = "noPeriod", type = Long.class),
                        @ColumnResult(name = "noPayments", type = Long.class),
                        @ColumnResult(name = "credit", type = Long.class),
                        @ColumnResult(name = "interes", type = Double.class),
                        @ColumnResult(name = "interesF", type = Double.class),

                }
        )
)

@Schema(name = "AdmCreditCalculator")
@JsonbPropertyOrder({"calculatorId", "person", "applicationDate", "noPeriod",
        "noPayments", "interestRate", "createdBy", "dateCreated", "status"})
public class AdmCreditCalculator implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAccountSequence") //JPA
    @Column(name = "calculator_id")
    private Long calculatorId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @DefaultValue("0")
    private AdmPerson person;

    @NotNull
    @Column(name = "application_date")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime applicationDate;

    @Column(name = "no_period")
    @NotNull
    private Long noPeriod;

    @Column(name = "no_payments")
    @NotNull
    private Long noPayments;

    @NotNull
    @Column(name = "credit")
    private Double credit;

    @NotNull
    @Column(name = "interest_rate")
    private Double interestRate;

    @NotNull
    @Column(name = "interest_final")
    private Double interestFinal;



    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_line")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmCreditLine creditLine;

    public AdmCreditCalculator() {
    }

    public AdmCreditCalculator(@NotNull Long calculatorId, @NotNull AdmPerson person, @NotNull LocalDateTime applicationDate, @NotNull Long noPeriod, @NotNull Long noPayments, @NotNull Double credit, @NotNull Double interestRate, @NotNull Double interestFinal, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status, @NotNull AdmCreditLine creditLine) {
        this.calculatorId = calculatorId;
        this.person = person;
        this.applicationDate = applicationDate;
        this.noPeriod = noPeriod;
        this.noPayments = noPayments;
        this.credit = credit;
        this.interestRate = interestRate;
        this.interestFinal = interestFinal;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.creditLine = creditLine;
    }

    public Long getCalculatorId() {
        return calculatorId;
    }

    public void setCalculatorId(Long calculatorId) {
        this.calculatorId = calculatorId;
    }

    public AdmPerson getPerson() {
        return person;
    }

    public void setPerson(AdmPerson person) {
        this.person = person;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Long getNoPeriod() {
        return noPeriod;
    }

    public void setNoPeriod(Long noPeriod) {
        this.noPeriod = noPeriod;
    }

    public Long getNoPayments() {
        return noPayments;
    }

    public void setNoPayments(Long noPayments) {
        this.noPayments = noPayments;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInterestFinal() {
        return interestFinal;
    }

    public void setInterestFinal(Double interestFinal) {
        this.interestFinal = interestFinal;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public AdmCreditLine getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(AdmCreditLine creditLine) {
        this.creditLine = creditLine;
    }

    @Override
    public String toString() {
        return "AdmCreditCalculator{" +
                "calculatorId=" + calculatorId +
                ", person=" + person +
                ", applicationDate=" + applicationDate +
                ", noPeriod=" + noPeriod +
                ", noPayments=" + noPayments +
                ", credit=" + credit +
                ", interestRate=" + interestRate +
                ", interestFinal=" + interestFinal +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", creditLine=" + creditLine +
                '}';
    }
}
