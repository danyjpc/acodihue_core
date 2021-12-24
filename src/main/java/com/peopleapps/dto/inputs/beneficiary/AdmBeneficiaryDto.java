package com.peopleapps.dto.inputs.beneficiary;

import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.model.AdmBeneficiaryAccount;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@JsonbPropertyOrder({"beneficiaryId", "beneficiaryAccount", "person", "kinship",
        "status", "phone", "dateCreated", "percent", "age", "createdBy"})
public class AdmBeneficiaryDto {
    private Long beneficiaryId;
    private AdmBeneficiaryPersonDto person;
    private AdmBeneficiaryTypologyDto kinship;
    private AdmBeneficiaryTypologyDto status;
    private AdmBeneficiaryPhoneDto phone;
    private LocalDateTime dateCreated;
    private Double percent;
    private Long age;
    private AdmBeneficiaryPersonDto createdBy;
    private AdmBeneficiaryAccount beneficiaryAccount;

    public AdmBeneficiaryDto() {
    }



    public AdmBeneficiaryDto(AdmBeneficiaryPersonDto person, AdmBeneficiaryTypologyDto kinship, AdmBeneficiaryTypologyDto status, AdmBeneficiaryPhoneDto phone, Double percent, Long age, AdmBeneficiaryPersonDto createdBy) {
        this.person = person;
        this.kinship = kinship;
        this.status = status;
        this.phone = phone;
        this.percent = percent;
        this.age = age;
        this.createdBy = createdBy;
    }

    public AdmBeneficiaryDto(Long beneficiaryId, AdmBeneficiaryPersonDto person, AdmBeneficiaryTypologyDto kinship, AdmBeneficiaryTypologyDto status, AdmBeneficiaryPhoneDto phone, LocalDateTime dateCreated, Double percent, Long age, AdmBeneficiaryPersonDto createdBy, AdmBeneficiaryAccount beneficiaryAccount) {
        this.beneficiaryId = beneficiaryId;
        this.person = person;
        this.kinship = kinship;
        this.status = status;
        this.phone = phone;
        this.dateCreated = dateCreated;
        this.percent = percent;
        this.age = age;
        this.createdBy = createdBy;
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public AdmBeneficiaryAccount getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(AdmBeneficiaryAccount beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public AdmBeneficiaryPersonDto getPerson() {
        return person;
    }

    public void setPerson(AdmBeneficiaryPersonDto person) {
        this.person = person;
    }

    public AdmBeneficiaryTypologyDto getKinship() {
        return kinship;
    }

    public void setKinship(AdmBeneficiaryTypologyDto kinship) {
        this.kinship = kinship;
    }

    public AdmBeneficiaryTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmBeneficiaryTypologyDto status) {
        this.status = status;
    }

    public AdmBeneficiaryPhoneDto getPhone() {
        return phone;
    }

    public void setPhone(AdmBeneficiaryPhoneDto phone) {
        this.phone = phone;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmBeneficiaryPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmBeneficiaryPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public static class AdmBeneficiaryPersonDto{
        private UUID personKey;
        private String firstName;
        private String lastName;
        @JsonbDateFormat(value = CsnConstants.ONLY_DATE_FORMAT)
        private LocalDate birthday;
        private String email;
        private String nameComplete;

        public AdmBeneficiaryPersonDto() {
        }

        public AdmBeneficiaryPersonDto(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public AdmBeneficiaryPersonDto(UUID personKey, String firstName, String lastName, LocalDate birthday, String email, String nameComplete) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.email = email;
            this.nameComplete = nameComplete;
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
    }

    public static class AdmBeneficiaryTypologyDto {
        private Long typologyId;
        private String description;

        public AdmBeneficiaryTypologyDto() {
        }

        public AdmBeneficiaryTypologyDto(Long typologyId) {
            this.typologyId = typologyId;
        }

        public AdmBeneficiaryTypologyDto(Long typologyId, String description) {
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

    public static class AdmBeneficiaryPhoneDto {
        private Long phoneId;
        private Long phone;
        private AdmBeneficiaryTypologyDto type;
        private AdmBeneficiaryTypologyDto status;

        public AdmBeneficiaryPhoneDto() {
        }

        public AdmBeneficiaryPhoneDto(Long phone) {
            this.phone = phone;
        }

        public AdmBeneficiaryPhoneDto(Long phoneId, Long phone, AdmBeneficiaryTypologyDto type, AdmBeneficiaryTypologyDto status) {
            this.phoneId = phoneId;
            this.phone = phone;
            this.type = type;
            this.status = status;
        }

        public AdmBeneficiaryTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmBeneficiaryTypologyDto status) {
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

        public AdmBeneficiaryTypologyDto getType() {
            return type;
        }

        public void setType(AdmBeneficiaryTypologyDto type) {
            this.type = type;
        }
    }


}
