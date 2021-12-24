package com.peopleapps.dto.beneficiary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmBeneficiaryListDto {
    private Long beneficiaryId;
    private Long beneficiaryAccountId;
    private UUID personBeneficiaryKey;
    private String personBeneficiaryFirstName;
    private String personBeneficiaryLastName;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDesc;
    private Long kinshipId;
    private String kinshipDesc;
    private Double percent;
    private Long age;
    private String personBeneficiaryNameComplete;
    private Long phoneId;
    private Long phone;
    private LocalDate birthday;
    private String email;


    public AdmBeneficiaryListDto(Long beneficiaryId, Long beneficiaryAccountId, UUID personBeneficiaryKey, String personBeneficiaryFirstName, String personBeneficiaryLastName, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDesc, Long kinshipId, String kinshipDesc, Double percent, Long age, String personBeneficiaryNameComplete, Long phoneId, Long phone, LocalDate birthday, String email) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryAccountId = beneficiaryAccountId;
        this.personBeneficiaryKey = personBeneficiaryKey;
        this.personBeneficiaryFirstName = personBeneficiaryFirstName;
        this.personBeneficiaryLastName = personBeneficiaryLastName;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.kinshipId = kinshipId;
        this.kinshipDesc = kinshipDesc;
        this.percent = percent;
        this.age = age;
        this.personBeneficiaryNameComplete = personBeneficiaryNameComplete;
        this.phoneId = phoneId;
        this.phone = phone;
        this.birthday = birthday;
        this.email = email;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public Long getBeneficiaryAccountId() {
        return beneficiaryAccountId;
    }

    public void setBeneficiaryAccountId(Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }

    public UUID getPersonBeneficiaryKey() {
        return personBeneficiaryKey;
    }

    public void setPersonBeneficiaryKey(UUID personBeneficiaryKey) {
        this.personBeneficiaryKey = personBeneficiaryKey;
    }

    public String getPersonBeneficiaryFirstName() {
        return personBeneficiaryFirstName;
    }

    public void setPersonBeneficiaryFirstName(String personBeneficiaryFirstName) {
        this.personBeneficiaryFirstName = personBeneficiaryFirstName;
    }

    public String getPersonBeneficiaryLastName() {
        return personBeneficiaryLastName;
    }

    public void setPersonBeneficiaryLastName(String personBeneficiaryLastName) {
        this.personBeneficiaryLastName = personBeneficiaryLastName;
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

    public Long getKinshipId() {
        return kinshipId;
    }

    public void setKinshipId(Long kinshipId) {
        this.kinshipId = kinshipId;
    }

    public String getKinshipDesc() {
        return kinshipDesc;
    }

    public void setKinshipDesc(String kinshipDesc) {
        this.kinshipDesc = kinshipDesc;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getPersonBeneficiaryNameComplete() {
        return personBeneficiaryNameComplete;
    }

    public void setPersonBeneficiaryNameComplete(String personBeneficiaryNameComplete) {
        this.personBeneficiaryNameComplete = personBeneficiaryNameComplete;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
