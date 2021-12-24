package com.peopleapps.dto.partner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmPartnerListDto {
    private Long partnerId;
    private UUID personKey;
    private String personFirstName;
    private String personLastName;
    private Long cui;
    private UUID partnerPersonKey;
    private String partnerFirstName;
    private String partnerLastName;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDesc;
    private Long noBoys;
    private Long noGirls;
    private Boolean leader;
    private Long age;
    private LocalDate personBirthday;
    private String personMail;
    private String personNameComplete;
    private Long personPhoneId;
    private Long personPhone;
    private Long phoneStatusId;
    private String phoneStatusDesc;
    private Long phoneTypeId;
    private String phoneTypeDesc;

    public AdmPartnerListDto(Long partnerId, UUID personKey, String personFirstName, String personLastName, Long cui, UUID partnerPersonKey, String partnerFirstName, String partnerLastName, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDesc, Long noBoys, Long noGirls, Boolean leader, Long age, LocalDate personBirthday, String personMail, String personNameComplete, Long personPhoneId, Long personPhone, Long phoneStatusId, String phoneStatusDesc, Long phoneTypeId, String phoneTypeDesc) {
        this.partnerId = partnerId;
        this.personKey = personKey;
        this.personFirstName = personFirstName;
        this.personLastName = personLastName;
        this.partnerPersonKey = partnerPersonKey;
        this.partnerFirstName = partnerFirstName;
        this.partnerLastName = partnerLastName;
        this.cui = cui;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.noBoys = noBoys;
        this.noGirls = noGirls;
        this.leader = leader;
        this.age = age;
        this.personBirthday = personBirthday;
        this.personMail = personMail;
        this.personNameComplete = personNameComplete;
        this.personPhoneId = personPhoneId;
        this.personPhone = personPhone;
        this.phoneStatusId = phoneStatusId;
        this.phoneStatusDesc = phoneStatusDesc;
        this.phoneTypeId = phoneTypeId;
        this.phoneTypeDesc = phoneTypeDesc;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    public UUID getPartnerPersonKey() {
        return partnerPersonKey;
    }

    public void setPartnerPersonKey(UUID partnerPersonKey) {
        this.partnerPersonKey = partnerPersonKey;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }



    public String getPartnerFirstName() {
        return partnerFirstName;
    }

    public void setPartnerFirstName(String partnerFirstName) {
        this.partnerFirstName = partnerFirstName;
    }

    public String getPartnerLastName() {
        return partnerLastName;
    }

    public void setPartnerLastName(String partnerLastName) {
        this.partnerLastName = partnerLastName;
    }

    public Long getCui() {
        return cui;
    }

    public void setCui(Long cui) {
        this.cui = cui;
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

    public Long getNoBoys() {
        return noBoys;
    }

    public void setNoBoys(Long noBoys) {
        this.noBoys = noBoys;
    }

    public Long getNoGirls() {
        return noGirls;
    }

    public void setNoGirls(Long noGirls) {
        this.noGirls = noGirls;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public LocalDate getPersonBirthday() {
        return personBirthday;
    }

    public void setPersonBirthday(LocalDate personBirthday) {
        this.personBirthday = personBirthday;
    }

    public String getPersonMail() {
        return personMail;
    }

    public void setPersonMail(String personMail) {
        this.personMail = personMail;
    }

    public String getPersonNameComplete() {
        return personNameComplete;
    }

    public void setPersonNameComplete(String personNameComplete) {
        this.personNameComplete = personNameComplete;
    }

    public Long getPersonPhoneId() {
        return personPhoneId;
    }

    public void setPersonPhoneId(Long personPhoneId) {
        this.personPhoneId = personPhoneId;
    }

    public Long getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(Long personPhone) {
        this.personPhone = personPhone;
    }

    public Long getPhoneStatusId() {
        return phoneStatusId;
    }

    public void setPhoneStatusId(Long phoneStatusId) {
        this.phoneStatusId = phoneStatusId;
    }

    public String getPhoneStatusDesc() {
        return phoneStatusDesc;
    }

    public void setPhoneStatusDesc(String phoneStatusDesc) {
        this.phoneStatusDesc = phoneStatusDesc;
    }

    public Long getPhoneTypeId() {
        return phoneTypeId;
    }

    public void setPhoneTypeId(Long phoneTypeId) {
        this.phoneTypeId = phoneTypeId;
    }

    public String getPhoneTypeDesc() {
        return phoneTypeDesc;
    }

    public void setPhoneTypeDesc(String phoneTypeDesc) {
        this.phoneTypeDesc = phoneTypeDesc;
    }
}
