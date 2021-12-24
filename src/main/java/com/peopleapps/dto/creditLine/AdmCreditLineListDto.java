package com.peopleapps.dto.creditLine;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmCreditLineListDto {
    private Long creditLineId;
    private UUID organizationKey;
    private String organizationName;
    private String organizationCommercial;
    private String description;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDesc;

    public AdmCreditLineListDto() {
    }

    public AdmCreditLineListDto(Long creditLineId, UUID organizationKey, String organizationName, String organizationCommercial, String description, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDesc) {
        this.creditLineId = creditLineId;
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.description = description;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
    }

    public Long getCreditLineId() {
        return creditLineId;
    }

    public void setCreditLineId(Long creditLineId) {
        this.creditLineId = creditLineId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
