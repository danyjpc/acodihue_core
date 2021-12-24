package com.peopleapps.dto.associationResponsible;

import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.organization.AdmOrganizationDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"associationResponsibleId", "organization", "person", "admissionDate",
        "dischargeDate", "annotation", "createdBy", "dateCreated", "status"})
public class AdmAssociationResponsibleDto {

    private Long associationResponsibleId;
    private AdmOrganizationDto organization;
    private AdmPersonDto person;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime admissionDate;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dischargeDate;
    private String annotation;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmTypologyDto status;

    public AdmAssociationResponsibleDto() {
    }

    public AdmAssociationResponsibleDto(Long associationResponsibleId, AdmOrganizationDto organization, AdmPersonDto person, LocalDateTime admissionDate, LocalDateTime dischargeDate, String annotation, AdmUserInfoDto.AdmPersonUserInfoDto createdBy, LocalDateTime dateCreated, AdmTypologyDto status) {
        this.associationResponsibleId = associationResponsibleId;
        this.organization = organization;
        this.person = person;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.annotation = annotation;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getAssociationResponsibleId() {
        return associationResponsibleId;
    }

    public void setAssociationResponsibleId(Long associationResponsibleId) {
        this.associationResponsibleId = associationResponsibleId;
    }

    public AdmOrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(AdmOrganizationDto organization) {
        this.organization = organization;
    }

    public AdmPersonDto getPerson() {
        return person;
    }

    public void setPerson(AdmPersonDto person) {
        this.person = person;
    }

    public LocalDateTime getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDateTime getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
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
