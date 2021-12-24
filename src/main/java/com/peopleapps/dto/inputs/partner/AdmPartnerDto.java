package com.peopleapps.dto.inputs.partner;

import com.peopleapps.dto.inputs.beneficiary.AdmBeneficiaryDto;
import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.model.AdmBeneficiaryAccount;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@JsonbPropertyOrder({"partnerId", "person", "partner","status", "phone",
        "dateCreated", "noBoys", "noGirls", "isLeader", "createdBy"})
public class AdmPartnerDto {
    private Long partnerId;
    private AdmPartnerPersonDto person;
    private AdmPartnerTypologyDto status;
    private AdmPartnerPhoneDto phone;
    private LocalDateTime dateCreated;
    private Long noBoys;
    private Long noGirls;
    private AdmPartnerPersonDto createdBy;
    //noBoys + noGirls
    private Long childrenSum;
    private Boolean isLeader;

    public AdmPartnerDto() {
    }

    public AdmPartnerDto(Long partnerId, AdmPartnerPersonDto person, AdmPartnerTypologyDto status, AdmPartnerPhoneDto phone, LocalDateTime dateCreated, Long noBoys, Long noGirls, AdmPartnerPersonDto createdBy, Long childrenSum, Boolean leader) {
        this.partnerId = partnerId;
        this.person = person;
        this.status = status;
        this.phone = phone;
        this.dateCreated = dateCreated;
        this.noBoys = noBoys;
        this.noGirls = noGirls;
        this.createdBy = createdBy;
        this.childrenSum = childrenSum;
        this.isLeader = leader;
    }
    //Usado para el partner credit
    public AdmPartnerDto(Long partnerId, AdmPartnerPersonDto person, AdmPartnerTypologyDto status, AdmPartnerPhoneDto phone, LocalDateTime dateCreated) {
        this.partnerId = partnerId;
        this.person = person;
        this.status = status;
        this.phone = phone;
        this.dateCreated = dateCreated;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public AdmPartnerPersonDto getPerson() {
        return person;
    }

    public void setPerson(AdmPartnerPersonDto person) {
        this.person = person;
    }

    public AdmPartnerTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmPartnerTypologyDto status) {
        this.status = status;
    }

    public AdmPartnerPhoneDto getPhone() {
        return phone;
    }

    public void setPhone(AdmPartnerPhoneDto phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
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

    public AdmPartnerPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmPartnerPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public Long getChildrenSum() {
        return childrenSum;
    }

    public void setChildrenSum(Long childrenSum) {
        this.childrenSum = childrenSum;
    }

    public Boolean getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Boolean leader) {
        isLeader = leader;
    }

    public static class AdmPartnerPersonDto{
        private UUID personKey;
        private String firstName;
        private String lastName;
        private Long cui;
        @JsonbDateFormat(value = CsnConstants.ONLY_DATE_FORMAT)
        private LocalDate birthday;
        private String email;
        private String nameComplete;
        private AdmPartnerTypologyDto profession;
        private AdmPartnerTypologyDto maritalStatus;

        public AdmPartnerPersonDto() {
        }

        public AdmPartnerPersonDto(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public AdmPartnerPersonDto(UUID personKey, String firstName, String lastName, LocalDate birthday, String email, String nameComplete) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.email = email;
            this.nameComplete = nameComplete;
        }
        public AdmPartnerPersonDto(UUID personKey, String firstName, String lastName, Long cui, LocalDate birthday, String email, String nameComplete) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.cui = cui;
            this.birthday = birthday;
            this.email = email;
            this.nameComplete = nameComplete;
        }

        public AdmPartnerPersonDto(UUID personKey, String firstName, String lastName, Long cui, LocalDate birthday, String email, String nameComplete, AdmPartnerTypologyDto profession, AdmPartnerTypologyDto marritalStatus) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.cui = cui;
            this.birthday = birthday;
            this.email = email;
            this.nameComplete = nameComplete;
            this.profession = profession;
            this.maritalStatus = marritalStatus;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Long getCui() {
            return cui;
        }

        public void setCui(Long cui) {
            this.cui = cui;
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

        public String getNameComplete() {
            return nameComplete;
        }

        public void setNameComplete(String nameComplete) {
            this.nameComplete = nameComplete;
        }

        public AdmPartnerTypologyDto getProfession() {
            return profession;
        }

        public void setProfession(AdmPartnerTypologyDto profession) {
            this.profession = profession;
        }

        public AdmPartnerTypologyDto getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(AdmPartnerTypologyDto maritalStatus) {
            this.maritalStatus = maritalStatus;
        }
    }

    public static class AdmPartnerTypologyDto {
        private Long typologyId;
        private String description;

        public AdmPartnerTypologyDto() {
        }

        public AdmPartnerTypologyDto(Long typologyId) {
            this.typologyId = typologyId;
        }

        public AdmPartnerTypologyDto(Long typologyId, String description) {
            this.typologyId = typologyId;
            this.description = description;
        }

        public Long getTypologyId() {
            return typologyId;
        }

        public void setTypologyId(Long typologyId) {
            this.typologyId = typologyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class AdmPartnerPhoneDto {
        private Long phoneId;
        private Long phone;
        private AdmPartnerTypologyDto type;
        private AdmPartnerTypologyDto status;

        public AdmPartnerPhoneDto() {
        }

        public AdmPartnerPhoneDto(Long phone) {
            this.phone = phone;
        }

        public AdmPartnerPhoneDto(Long phoneId, Long phone, AdmPartnerTypologyDto type, AdmPartnerTypologyDto status) {
            this.phoneId = phoneId;
            this.phone = phone;
            this.type = type;
            this.status = status;
        }

        public AdmPartnerTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmPartnerTypologyDto status) {
            this.status = status;
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

        public AdmPartnerTypologyDto getType() {
            return type;
        }

        public void setType(AdmPartnerTypologyDto type) {
            this.type = type;
        }


    }

}
