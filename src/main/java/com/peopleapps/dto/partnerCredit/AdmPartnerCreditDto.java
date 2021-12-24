package com.peopleapps.dto.partnerCredit;

import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;

@JsonbPropertyOrder({"partnerCreditId", "credit", "person", "createdBy", "dateCreated", "status"})
public class AdmPartnerCreditDto {

    private Long partnerCreditId;
    private AdmCreditDto credit;
    private AdmPersonDto person;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;
    @JsonbDateFormat(CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmTypologyDto status;

    public AdmPartnerCreditDto() {
    }

    public AdmPartnerCreditDto(Long partnerCreditId, AdmCreditDto credit, AdmPersonDto person, AdmUserInfoDto.AdmPersonUserInfoDto createdBy, LocalDateTime dateCreated, AdmTypologyDto status) {
        this.partnerCreditId = partnerCreditId;
        this.credit = credit;
        this.person = person;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getPartnerCreditId() {
        return partnerCreditId;
    }

    public void setPartnerCreditId(Long partnerCreditId) {
        this.partnerCreditId = partnerCreditId;
    }

    public AdmCreditDto getCredit() {
        return credit;
    }

    public void setCredit(AdmCreditDto credit) {
        this.credit = credit;
    }

    public AdmPersonDto getPerson() {
        return person;
    }

    public void setPerson(AdmPersonDto person) {
        this.person = person;
    }

    public AdmUserInfoDto.AdmPersonUserInfoDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUserInfoDto.AdmPersonUserInfoDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyDto status) {
        this.status = status;
    }
}
