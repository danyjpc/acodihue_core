package com.peopleapps.dto.phone;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmPhoneListDto {
    private Long phoneId;
    private Long phoneAccountId;
    private Long phone;
    private Long statusId;
    private String statusDesc;
    private Long typeId;
    private String typeDesc;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Boolean leader;

    public AdmPhoneListDto(Long phoneId, Long phoneAccountId, Long phone, Long statusId, String statusDesc, Long typeId, String typeDesc, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Boolean leader) {
        this.phoneId = phoneId;
        this.phoneAccountId = phoneAccountId;
        this.phone = phone;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.typeId = typeId;
        this.typeDesc = typeDesc;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.leader = leader;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getPhoneAccountId() {
        return phoneAccountId;
    }

    public void setPhoneAccountId(Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }
}
