package com.peopleapps.dto.partnerCredit;

import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmPartnerCreditListDto {

    private Long partnerCreditId;
    //credit
    private Long creditId;
    private Long creditTypeTypoId;
    private String creditTypeTypoDesc;
    //calculator
    private Long calculatorId;
    private Long noPayments;
    private BigDecimal interestRate;
    private BigDecimal credit;
    //person
    private UUID personKey;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameComplete;
    //created by
    private UUID createdByPersonKey;
    private String createdByEmail;
    private LocalDateTime dateCreated;
    //status
    private Long statusTypoId;
    private String statusTypoDesc;
    //creditKey
    private UUID creditKey;
    //partner mail and birthday
    private String partnerEmail;
    private LocalDate partnerBirthday;

    public AdmPartnerCreditListDto() {
    }

    public AdmPartnerCreditListDto(Long partnerCreditId, Long creditId, Long creditTypeTypoId, String creditTypeTypoDesc, Long calculatorId, Long noPayments, BigDecimal interestRate, BigDecimal credit, UUID personKey, String firstName, String middleName, String lastName, String nameComplete, UUID createdByPersonKey, String createdByEmail, LocalDateTime dateCreated, Long statusTypoId, String statusTypoDesc, UUID creditKey, String partnerEmail, LocalDate partnerBirthday) {
        this.partnerCreditId = partnerCreditId;
        this.creditId = creditId;
        this.creditTypeTypoId = creditTypeTypoId;
        this.creditTypeTypoDesc = creditTypeTypoDesc;
        this.calculatorId = calculatorId;
        this.noPayments = noPayments;
        this.interestRate = interestRate;
        this.credit = credit;
        this.personKey = personKey;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameComplete = nameComplete;
        this.createdByPersonKey = createdByPersonKey;
        this.createdByEmail = createdByEmail;
        this.dateCreated = dateCreated;
        this.statusTypoId = statusTypoId;
        this.statusTypoDesc = statusTypoDesc;
        this.creditKey = creditKey;
        this.partnerEmail = partnerEmail;
        this.partnerBirthday = partnerBirthday;
    }

    public Long getPartnerCreditId() {
        return partnerCreditId;
    }

    public void setPartnerCreditId(Long partnerCreditId) {
        this.partnerCreditId = partnerCreditId;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Long getCreditTypeTypoId() {
        return creditTypeTypoId;
    }

    public void setCreditTypeTypoId(Long creditTypeTypoId) {
        this.creditTypeTypoId = creditTypeTypoId;
    }

    public String getCreditTypeTypoDesc() {
        return creditTypeTypoDesc;
    }

    public void setCreditTypeTypoDesc(String creditTypeTypoDesc) {
        this.creditTypeTypoDesc = creditTypeTypoDesc;
    }

    public Long getCalculatorId() {
        return calculatorId;
    }

    public void setCalculatorId(Long calculatorId) {
        this.calculatorId = calculatorId;
    }

    public Long getNoPayments() {
        return noPayments;
    }

    public void setNoPayments(Long noPayments) {
        this.noPayments = noPayments;
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

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public UUID getCreatedByPersonKey() {
        return createdByPersonKey;
    }

    public void setCreatedByPersonKey(UUID createdByPersonKey) {
        this.createdByPersonKey = createdByPersonKey;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getStatusTypoId() {
        return statusTypoId;
    }

    public void setStatusTypoId(Long statusTypoId) {
        this.statusTypoId = statusTypoId;
    }

    public String getStatusTypoDesc() {
        return statusTypoDesc;
    }

    public void setStatusTypoDesc(String statusTypoDesc) {
        this.statusTypoDesc = statusTypoDesc;
    }

    public UUID getCreditKey() {
        return creditKey;
    }

    public void setCreditKey(UUID creditKey) {
        this.creditKey = creditKey;
    }

    public String getPartnerEmail() {
        return partnerEmail;
    }

    public void setPartnerEmail(String partnerEmail) {
        this.partnerEmail = partnerEmail;
    }

    public LocalDate getPartnerBirthday() {
        return partnerBirthday;
    }

    public void setPartnerBirthday(LocalDate partnerBirthday) {
        this.partnerBirthday = partnerBirthday;
    }
}
