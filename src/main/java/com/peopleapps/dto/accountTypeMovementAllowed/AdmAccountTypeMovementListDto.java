package com.peopleapps.dto.accountTypeMovementAllowed;

import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAccountTypeMovementListDto {

    private Long admAccountAllowedMovementsId;
    private Long accountTypeId;
    private String accountTypeDesc;
    private Long operationId;
    private String operationDesc;
    private Long operationTypeId;
    private String operationTypeDesc;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDescription;


    public AdmAccountTypeMovementListDto() {
    }

    public AdmAccountTypeMovementListDto(Long admAccountAllowedMovementsId, Long accountTypeId, String accountTypeDesc) {
        this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
        this.accountTypeId = accountTypeId;
        this.accountTypeDesc = accountTypeDesc;
    }

    public AdmAccountTypeMovementListDto(Long admAccountAllowedMovementsId, Long accountTypeId, String accountTypeDesc, Long operationId, String operationDesc, Long operationTypeId, String operationTypeDesc, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDescription) {
        this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
        this.accountTypeId = accountTypeId;
        this.accountTypeDesc = accountTypeDesc;
        this.operationId = operationId;
        this.operationDesc = operationDesc;
        this.operationTypeId = operationTypeId;
        this.operationTypeDesc = operationTypeDesc;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDescription = statusDescription;
    }

    public Long getAdmAccountAllowedMovementsId() {
        return admAccountAllowedMovementsId;
    }

    public void setAdmAccountAllowedMovementsId(Long admAccountAllowedMovementsId) {
        this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeDesc() {
        return accountTypeDesc;
    }

    public void setAccountTypeDesc(String accountTypeDesc) {
        this.accountTypeDesc = accountTypeDesc;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getOperationTypeDesc() {
        return operationTypeDesc;
    }

    public void setOperationTypeDesc(String operationTypeDesc) {
        this.operationTypeDesc = operationTypeDesc;
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
}
