package com.peopleapps.dto.phone;

import com.peopleapps.dto.multiAccount.AdmPhoneAccountDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"phoneId","phoneAccount","phone",
        "status","type","createdBy","dateCreated","leader"})
public class AdmPhoneDto {
    private Long phoneId;
    private AdmPhoneAccountDto phoneAccount;
    private Long phone;
    private AdmTypologyDto status;
    private AdmTypologyDto type;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;
    private LocalDateTime dateCreated;
    private Boolean leader;

    public AdmPhoneDto() {
    }

    public AdmPhoneDto(Long phoneId, AdmPhoneAccountDto phoneAccount, Long phone, AdmTypologyDto status, AdmTypologyDto type, AdmUserInfoDto.AdmPersonUserInfoDto createdBy, LocalDateTime dateCreated, Boolean leader) {
        this.phoneId = phoneId;
        this.phoneAccount = phoneAccount;
        this.phone = phone;
        this.status = status;
        this.type = type;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.leader = leader;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public AdmPhoneAccountDto getPhoneAccount() {
        return phoneAccount;
    }

    public void setPhoneAccount(AdmPhoneAccountDto phoneAccount) {
        this.phoneAccount = phoneAccount;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public AdmTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyDto status) {
        this.status = status;
    }

    public AdmTypologyDto getType() {
        return type;
    }

    public void setType(AdmTypologyDto type) {
        this.type = type;
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

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }
}
