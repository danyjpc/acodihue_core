package com.peopleapps.dto.associationResponsible;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAssociationResponsibleListDto {

    private Long associationResponsibleId;
    private UUID organizationKey;
    private String organizationName;
    private String organizationCommercial;
    private UUID personKey;
    private String persoNameComplete;
    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;
    private String annotation;
    private UUID createdByPersonKey;
    private String createdByEmail;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDesc;
    private Double interestRate;

    public AdmAssociationResponsibleListDto() {
    }

    public AdmAssociationResponsibleListDto(Long associationResponsibleId, UUID organizationKey, String organizationName, String organizationCommercial, UUID personKey, String persoNameComplete, LocalDateTime admissionDate, LocalDateTime dischargeDate, String annotation, UUID createdByPersonKey, String createdByEmail, LocalDateTime dateCreated, Long statusId, String statusDesc, Double interestRate) {
        this.associationResponsibleId = associationResponsibleId;
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.personKey = personKey;
        this.persoNameComplete = persoNameComplete;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.annotation = annotation;
        this.createdByPersonKey = createdByPersonKey;
        this.createdByEmail = createdByEmail;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.interestRate = interestRate;
    }

    public Long getAssociationResponsibleId() {
        return associationResponsibleId;
    }

    public void setAssociationResponsibleId(Long associationResponsibleId) {
        this.associationResponsibleId = associationResponsibleId;
    }

    public UUID getOrganizationKey() {
        return organizationKey;
    }

    public void setOrganizationKey(UUID organizationKey) {
        this.organizationKey = organizationKey;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCommercial() {
        return organizationCommercial;
    }

    public void setOrganizationCommercial(String organizationCommercial) {
        this.organizationCommercial = organizationCommercial;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    public String getPersoNameComplete() {
        return persoNameComplete;
    }

    public void setPersoNameComplete(String persoNameComplete) {
        this.persoNameComplete = persoNameComplete;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
