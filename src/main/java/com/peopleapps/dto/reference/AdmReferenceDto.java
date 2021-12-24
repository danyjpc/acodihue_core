package com.peopleapps.dto.reference;

import com.peopleapps.dto.multiAccount.AdmReferenceAccountDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.phone.AdmPhoneDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;
@JsonbPropertyOrder({"referenceId","referenceAccount","reference",
        "createdBy","dateCreated","status","kinship","age", "phone"})
public class AdmReferenceDto {
    private Long referenceId;
    private AdmReferenceAccountDto referenceAccount;
    private AdmPersonDto person;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmTypologyDto status;
    private AdmTypologyDto kinship;
    private Long age;
    private AdmPhoneDto phone;

    public AdmReferenceDto() {
    }

    public AdmReferenceDto(Long referenceId, AdmReferenceAccountDto referenceAccount, AdmPersonDto person, AdmUserInfoDto.AdmPersonUserInfoDto createdBy, LocalDateTime dateCreated, AdmTypologyDto status, AdmTypologyDto kinship, Long age, AdmPhoneDto phone) {
        this.referenceId = referenceId;
        this.referenceAccount = referenceAccount;
        this.person = person;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.kinship = kinship;
        this.age = age;
        this.phone = phone;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public AdmReferenceAccountDto getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(AdmReferenceAccountDto referenceAccount) {
        this.referenceAccount = referenceAccount;
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

    public AdmTypologyDto getKinship() {
        return kinship;
    }

    public void setKinship(AdmTypologyDto kinship) {
        this.kinship = kinship;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public AdmPhoneDto getPhone() {
        return phone;
    }

    public void setPhone(AdmPhoneDto phone) {
        this.phone = phone;
    }
}
