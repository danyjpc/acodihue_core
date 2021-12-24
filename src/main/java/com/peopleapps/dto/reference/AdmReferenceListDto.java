package com.peopleapps.dto.reference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmReferenceListDto {
    private Long referenceId;
    private Long referenceAccountId;
    private UUID referencePersonKey;
    private String referenceFirstName;
    private String referenceLastName;
    private String referenceNameComplete;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusTypologyId;
    private String statusDescription;
    private Long kinshipTypologyId;
    private String kinshipDescription;
    private Long age;
    private Long phoneAccountId;
    private Long phoneId;
    private Long phone;
    private Long phoneTypeTypologyId;
    private String phoneTypeDescription;
    private String referenceEmail;
    private LocalDate referenceBirthday;

    public AdmReferenceListDto(Long referenceId, Long referenceAccountId, UUID referencePersonKey, String referenceFirstName, String referenceLastName, String referenceNameComplete, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusTypologyId, String statusDescription, Long kinshipTypologyId, String kinshipDescription, Long age, Long phoneAccountId, Long phoneId, Long phone, Long phoneTypeTypologyId, String phoneTypeDescription, String referenceEmail, LocalDate referenceBirthday) {
        this.referenceId = referenceId;
        this.referenceAccountId = referenceAccountId;
        this.referencePersonKey = referencePersonKey;
        this.referenceFirstName = referenceFirstName;
        this.referenceLastName = referenceLastName;
        this.referenceNameComplete = referenceNameComplete;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusTypologyId = statusTypologyId;
        this.statusDescription = statusDescription;
        this.kinshipTypologyId = kinshipTypologyId;
        this.kinshipDescription = kinshipDescription;
        this.age = age;
        this.phoneAccountId = phoneAccountId;
        this.phoneId = phoneId;
        this.phone = phone;
        this.phoneTypeTypologyId = phoneTypeTypologyId;
        this.phoneTypeDescription = phoneTypeDescription;
        this.referenceEmail = referenceEmail;
        this.referenceBirthday = referenceBirthday;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Long getReferenceAccountId() {
        return referenceAccountId;
    }

    public void setReferenceAccountId(Long referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    public UUID getReferencePersonKey() {
        return referencePersonKey;
    }

    public void setReferencePersonKey(UUID referencePersonKey) {
        this.referencePersonKey = referencePersonKey;
    }

    public String getReferenceFirstName() {
        return referenceFirstName;
    }

    public void setReferenceFirstName(String referenceFirstName) {
        this.referenceFirstName = referenceFirstName;
    }

    public String getReferenceLastName() {
        return referenceLastName;
    }

    public void setReferenceLastName(String referenceLastName) {
        this.referenceLastName = referenceLastName;
    }

    public String getReferenceNameComplete() {
        return referenceNameComplete;
    }

    public void setReferenceNameComplete(String referenceNameComplete) {
        this.referenceNameComplete = referenceNameComplete;
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

    public Long getStatusTypologyId() {
        return statusTypologyId;
    }

    public void setStatusTypologyId(Long statusTypologyId) {
        this.statusTypologyId = statusTypologyId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Long getKinshipTypologyId() {
        return kinshipTypologyId;
    }

    public void setKinshipTypologyId(Long kinshipTypologyId) {
        this.kinshipTypologyId = kinshipTypologyId;
    }

    public String getKinshipDescription() {
        return kinshipDescription;
    }

    public void setKinshipDescription(String kinshipDescription) {
        this.kinshipDescription = kinshipDescription;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getPhoneAccountId() {
        return phoneAccountId;
    }

    public void setPhoneAccountId(Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
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

    public Long getPhoneTypeTypologyId() {
        return phoneTypeTypologyId;
    }

    public void setPhoneTypeTypologyId(Long phoneTypeTypologyId) {
        this.phoneTypeTypologyId = phoneTypeTypologyId;
    }

    public String getPhoneTypeDescription() {
        return phoneTypeDescription;
    }

    public void setPhoneTypeDescription(String phoneTypeDescription) {
        this.phoneTypeDescription = phoneTypeDescription;
    }

    public String getReferenceEmail() {
        return referenceEmail;
    }

    public void setReferenceEmail(String referenceEmail) {
        this.referenceEmail = referenceEmail;
    }

    public LocalDate getReferenceBirthday() {
        return referenceBirthday;
    }

    public void setReferenceBirthday(LocalDate referenceBirthday) {
        this.referenceBirthday = referenceBirthday;
    }
}
