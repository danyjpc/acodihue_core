package com.peopleapps.dto.creditCalculator;

import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmCreditCalculatorListDto {
    public final Long calculatorId;
    public final UUID personKey;
    public final String firstName;
    public final String lastName;
    public final LocalDateTime applicationDate;
    public final Long noPeriod;
    public final Long noPayments;
    public final Double interestRate;
    public final Double credit;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    public final LocalDateTime dateCreated;
    public final String createdByEmail;
    public final UUID createdBypersonKey;
    public final Long statusId;
    public final String statusDesc;
    public final Double interestFinal;
    public final UUID organizationKey;
    public final String organizationName;
    public final String organizationCommercial;
    public final Long creditLine;
    public final String creditLineDesc;
    public final Long membershipNumber;

    public AdmCreditCalculatorListDto(Long calculatorId, UUID personKey, String firstName, String lastName, LocalDateTime applicationDate, Long noPeriod, Long noPayments, Double interestRate, Double credit, LocalDateTime dateCreated, String createdByEmail, UUID createdBypersonKey, Long statusId, String statusDesc, Double interestFinal, UUID organizationKey, String organizationName, String organizationCommercial, Long creditLine, String creditLineDesc, Long membershipNumber) {
        this.calculatorId = calculatorId;
        this.personKey = personKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.applicationDate = applicationDate;
        this.noPeriod = noPeriod;
        this.noPayments = noPayments;
        this.interestRate = interestRate;
        this.credit = credit;
        this.dateCreated = dateCreated;
        this.createdByEmail = createdByEmail;
        this.createdBypersonKey = createdBypersonKey;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.interestFinal = interestFinal;
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.creditLine = creditLine;
        this.creditLineDesc = creditLineDesc;
        this.membershipNumber = membershipNumber;
    }
}
