package com.peopleapps.dto.creditCalculator;

import com.peopleapps.dto.creditLine.AdmCreditLineDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonbPropertyOrder({"calculatorId", "person", "applicationDate", "noPeriod",
        "noPayments", "interestRate", "createdBy", "dateCreated", "status"})
public class AdmCreditCalculatorDto {
    private Long calculatorId;
    private AdmPersonDto person;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime applicationDate;
    private Long noPeriod;
    private Long noPayments;
    private BigDecimal interestRate;
    private BigDecimal credit;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmPersonDto createdBy;
    private AdmTypologyDto status;
    private BigDecimal interestFinal;
    private AdmCreditLineDto creditLine;

    public AdmCreditCalculatorDto() {
    }

    public AdmCreditCalculatorDto(Long calculatorId, AdmPersonDto person, LocalDateTime applicationDate, Long noPeriod, Long noPayments, BigDecimal interestRate, BigDecimal credit, LocalDateTime dateCreated, AdmPersonDto createdBy, AdmTypologyDto status, BigDecimal interestFinal, AdmCreditLineDto creditLine) {
        this.calculatorId = calculatorId;
        this.person = person;
        this.applicationDate = applicationDate;
        this.noPeriod = noPeriod;
        this.noPayments = noPayments;
        this.interestRate = interestRate;
        this.credit = credit;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.status = status;
        this.interestFinal = interestFinal;
        this.creditLine = creditLine;
    }

    public AdmCreditCalculatorDto(AdmPersonDto person, LocalDateTime applicationDate, Long noPeriod, Long noPayments, BigDecimal interestRate, BigDecimal credit, LocalDateTime dateCreated, AdmPersonDto createdBy, AdmTypologyDto status, BigDecimal interestFinal, AdmCreditLineDto creditLine) {
        this.person = person;
        this.applicationDate = applicationDate;
        this.noPeriod = noPeriod;
        this.noPayments = noPayments;
        this.interestRate = interestRate;
        this.credit = credit;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.status = status;
        this.interestFinal = interestFinal;
        this.creditLine = creditLine;
    }

    public AdmCreditCalculatorDto(Long calculatorId, Long noPayments, BigDecimal interestRate, BigDecimal credit) {
        this.calculatorId = calculatorId;
        this.noPayments = noPayments;
        this.interestRate = interestRate;
        this.credit = credit;
    }

    public AdmCreditCalculatorDto(Long calculatorId) {
        this.calculatorId = calculatorId;
    }

    public Long getCalculatorId() {
        return calculatorId;
    }

    public void setCalculatorId(Long calculatorId) {
        this.calculatorId = calculatorId;
    }

    public AdmPersonDto getPerson() {
        return person;
    }

    public void setPerson(AdmPersonDto person) {
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



    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public AdmTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyDto status) {
        this.status = status;
    }



    public AdmCreditLineDto getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(AdmCreditLineDto creditLine) {
        this.creditLine = creditLine;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getInterestFinal() {
        return interestFinal;
    }

    public void setInterestFinal(BigDecimal interestFinal) {
        this.interestFinal = interestFinal;
    }
}
