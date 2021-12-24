package com.peopleapps.dto.credit;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmCreditListDto {
    private Long creditId;
    private Long noYear;
    private Long noReference;
    private Long addressAccountId;
    private Long referenceAccountId;
    private Long documentAccountId;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDescription;

    //new properties
    private Long activityAccount;
    private String internalCode;
    private LocalDateTime estateDate;
    private Long statusOperatedId;
    private String statusOperatedDescription;
    private String operatedByEmail;
    private UUID operatedByPersonKey;
    private String annotation;

    private Long organizationResponsibleId;
    private UUID organizationKey;
    private String organizationName;
    private String organizationCommercial;
    private UUID associatePersonKey;
    private String associateFirstName;
    private String associateLastName;
    private String associateNameComplete;

    private Long professionId;
    private String professionDescription;
    private Long occupationId;
    private String occupationDescription;
    private Boolean ownHouse;
    private Long calculatorId;
    private UUID creditKey;

    public AdmCreditListDto(Long creditId, Long noYear, Long noReference, Long addressAccountId, Long referenceAccountId, Long documentAccountId, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDescription, Long activityAccount, String internalCode, LocalDateTime estateDate, Long statusOperatedId, String statusOperatedDescription, String operatedByEmail, UUID operatedByPersonKey, String annotation, Long organizationResponsibleId, UUID organizationKey, String organizationName, String organizationCommercial, UUID associatePersonKey, String associateFirstName, String associateLastName, String associateNameComplete, Long professionId, String professionDescription, Long occupationId, String occupationDescription, Boolean ownHouse, Long calculatorId, UUID creditKey) {
        this.creditId = creditId;
        this.noYear = noYear;
        this.noReference = noReference;
        this.addressAccountId = addressAccountId;
        this.referenceAccountId = referenceAccountId;
        this.documentAccountId = documentAccountId;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDescription = statusDescription;
        this.activityAccount = activityAccount;
        this.internalCode = internalCode;
        this.estateDate = estateDate;
        this.statusOperatedId = statusOperatedId;
        this.statusOperatedDescription = statusOperatedDescription;
        this.operatedByEmail = operatedByEmail;
        this.operatedByPersonKey = operatedByPersonKey;
        this.annotation = annotation;
        this.organizationResponsibleId = organizationResponsibleId;
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.associatePersonKey = associatePersonKey;
        this.associateFirstName = associateFirstName;
        this.associateLastName = associateLastName;
        this.associateNameComplete = associateNameComplete;
        this.professionId = professionId;
        this.professionDescription = professionDescription;
        this.occupationId = occupationId;
        this.occupationDescription = occupationDescription;
        this.ownHouse = ownHouse;
        this.calculatorId = calculatorId;
        this.creditKey = creditKey;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Long getNoYear() {
        return noYear;
    }

    public void setNoYear(Long noYear) {
        this.noYear = noYear;
    }

    public Long getNoReference() {
        return noReference;
    }

    public void setNoReference(Long noReference) {
        this.noReference = noReference;
    }

    public Long getAddressAccountId() {
        return addressAccountId;
    }

    public void setAddressAccountId(Long addressAccountId) {
        this.addressAccountId = addressAccountId;
    }

    public Long getReferenceAccountId() {
        return referenceAccountId;
    }

    public void setReferenceAccountId(Long referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    public Long getDocumentAccountId() {
        return documentAccountId;
    }

    public void setDocumentAccountId(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public UUID getCreatedByPersonKey() {
        return createdByPersonKey;
    }

    public void setCreatedByPersonKey(UUID createdByPersonKey) {
        this.createdByPersonKey = createdByPersonKey;
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

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Long getActivityAccount() {
        return activityAccount;
    }

    public void setActivityAccount(Long activityAccount) {
        this.activityAccount = activityAccount;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public LocalDateTime getEstateDate() {
        return estateDate;
    }

    public void setEstateDate(LocalDateTime estateDate) {
        this.estateDate = estateDate;
    }

    public Long getStatusOperatedId() {
        return statusOperatedId;
    }

    public void setStatusOperatedId(Long statusOperatedId) {
        this.statusOperatedId = statusOperatedId;
    }

    public String getStatusOperatedDescription() {
        return statusOperatedDescription;
    }

    public void setStatusOperatedDescription(String statusOperatedDescription) {
        this.statusOperatedDescription = statusOperatedDescription;
    }

    public String getOperatedByEmail() {
        return operatedByEmail;
    }

    public void setOperatedByEmail(String operatedByEmail) {
        this.operatedByEmail = operatedByEmail;
    }

    public UUID getOperatedByPersonKey() {
        return operatedByPersonKey;
    }

    public void setOperatedByPersonKey(UUID operatedByPersonKey) {
        this.operatedByPersonKey = operatedByPersonKey;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Long getOrganizationResponsibleId() {
        return organizationResponsibleId;
    }

    public void setOrganizationResponsibleId(Long organizationResponsibleId) {
        this.organizationResponsibleId = organizationResponsibleId;
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

    public UUID getAssociatePersonKey() {
        return associatePersonKey;
    }

    public void setAssociatePersonKey(UUID associatePersonKey) {
        this.associatePersonKey = associatePersonKey;
    }

    public String getAssociateFirstName() {
        return associateFirstName;
    }

    public void setAssociateFirstName(String associateFirstName) {
        this.associateFirstName = associateFirstName;
    }

    public String getAssociateLastName() {
        return associateLastName;
    }

    public void setAssociateLastName(String associateLastName) {
        this.associateLastName = associateLastName;
    }

    public String getAssociateNameComplete() {
        return associateNameComplete;
    }

    public void setAssociateNameComplete(String associateNameComplete) {
        this.associateNameComplete = associateNameComplete;
    }

    public Long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Long professionId) {
        this.professionId = professionId;
    }

    public String getProfessionDescription() {
        return professionDescription;
    }

    public void setProfessionDescription(String professionDescription) {
        this.professionDescription = professionDescription;
    }

    public Long getOccupationId() {
        return occupationId;
    }

    public void setOccupationId(Long occupationId) {
        this.occupationId = occupationId;
    }

    public String getOccupationDescription() {
        return occupationDescription;
    }

    public void setOccupationDescription(String occupationDescription) {
        this.occupationDescription = occupationDescription;
    }

    public Boolean getOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(Boolean ownHouse) {
        this.ownHouse = ownHouse;
    }

    public Long getCalculatorId() {
        return calculatorId;
    }

    public void setCalculatorId(Long calculatorId) {
        this.calculatorId = calculatorId;
    }

    public UUID getCreditKey() {
        return creditKey;
    }

    public void setCreditKey(UUID creditKey) {
        this.creditKey = creditKey;
    }
}
