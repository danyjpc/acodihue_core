package com.peopleapps.dto.account;

import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"personAccount", "balance"})
public class AdmAccountDto {
    private AdmPersonAccountInfo personAccount;
    private Double balance;

    public AdmAccountDto() {
    }

    public AdmAccountDto(AdmPersonAccountInfo personAccount, Double balance) {
        this.personAccount = personAccount;
        this.balance = balance;
    }

    public AdmPersonAccountInfo getPersonAccount() {
        return personAccount;
    }

    public void setPersonAccount(AdmPersonAccountInfo personAccount) {
        this.personAccount = personAccount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @JsonbPropertyOrder({"account_id", "numAccount", "numAccountOrder",
            "organizationResponsible", "accountType", "date_created", "status", "createdBy"})
    public static class AdmPersonAccountInfo {
        private Long account_id;
        private AdmAccountOrganizationResponsibleDto organizationResponsible;
        private AdmAccountTypologyDto accountType;
        private AdmAccountPersonDto createdBy;
        private String numAccount;
        private Long numAccountOrder;
        @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
        private LocalDateTime date_created;
        private AdmAccountTypologyDto status;

        public AdmPersonAccountInfo() {
        }

        public AdmPersonAccountInfo(Long account_id, AdmAccountOrganizationResponsibleDto organizationResponsible, AdmAccountTypologyDto accountType, AdmAccountPersonDto createdBy, String numAccount, Long numAccountOrder, LocalDateTime date_created, AdmAccountTypologyDto status) {
            this.account_id = account_id;
            this.organizationResponsible = organizationResponsible;
            this.accountType = accountType;
            this.createdBy = createdBy;
            this.numAccount = numAccount;
            this.numAccountOrder = numAccountOrder;
            this.date_created = date_created;
            this.status = status;
        }

        public Long getAccount_id() {
            return account_id;
        }

        public void setAccount_id(Long account_id) {
            this.account_id = account_id;
        }

        public AdmAccountOrganizationResponsibleDto getOrganizationResponsible() {
            return organizationResponsible;
        }

        public void setOrganizationResponsible(AdmAccountOrganizationResponsibleDto organizationResponsible) {
            this.organizationResponsible = organizationResponsible;
        }

        public AdmAccountTypologyDto getAccountType() {
            return accountType;
        }

        public void setAccountType(AdmAccountTypologyDto accountType) {
            this.accountType = accountType;
        }

        public AdmAccountPersonDto getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(AdmAccountPersonDto createdBy) {
            this.createdBy = createdBy;
        }

        public String getNumAccount() {
            return numAccount;
        }

        public void setNumAccount(String numAccount) {
            this.numAccount = numAccount;
        }

        public Long getNumAccountOrder() {
            return numAccountOrder;
        }

        public void setNumAccountOrder(Long numAccountOrder) {
            this.numAccountOrder = numAccountOrder;
        }

        public LocalDateTime getDate_created() {
            return date_created;
        }

        public void setDate_created(LocalDateTime date_created) {
            this.date_created = date_created;
        }

        public AdmAccountTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmAccountTypologyDto status) {
            this.status = status;
        }
    }

    public static class AdmAccountTypologyDto {
        private Long typologyId;
        private String description;
        private String value_1;
        private String value_2;

        public AdmAccountTypologyDto() {
        }

        public AdmAccountTypologyDto(Long typologyId, String description, String value_1, String value_2) {
            this.typologyId = typologyId;
            this.description = description;
            this.value_1 = value_1;
            this.value_2 = value_2;
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

        public String getValue_1() {
            return value_1;
        }

        public void setValue_1(String value_1) {
            this.value_1 = value_1;
        }

        public String getValue_2() {
            return value_2;
        }

        public void setValue_2(String value_2) {
            this.value_2 = value_2;
        }
    }

    public static class AdmAccountPersonDto {
        private UUID personKey;
        private String firstName;
        private String lastName;
        private String email;
        private String nameComplete;

        public AdmAccountPersonDto() {
        }

        public AdmAccountPersonDto(UUID personKey, String firstName, String lastName, String email, String nameComplete) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
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

    public static class AdmAccountOrganizationDto {
        private UUID organizationKey;
        private String organizationName;
        private String organizationCommercial;

        public AdmAccountOrganizationDto() {
        }

        public AdmAccountOrganizationDto(UUID organizationKey, String organizationName, String organizationCommercial) {
            this.organizationKey = organizationKey;
            this.organizationName = organizationName;
            this.organizationCommercial = organizationCommercial;
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
    }

    public static class AdmAccountOrganizationResponsibleDto {
        private Long organizationResponsibleId;
        private AdmAccountOrganizationDto organization;
        private AdmAccountPersonDto person;

        public AdmAccountOrganizationResponsibleDto() {
        }

        public AdmAccountOrganizationResponsibleDto(Long organizationResponsibleId, AdmAccountOrganizationDto organization, AdmAccountPersonDto person) {
            this.organizationResponsibleId = organizationResponsibleId;
            this.organization = organization;
            this.person = person;
        }

        public AdmAccountOrganizationDto getOrganization() {
            return organization;
        }

        public void setOrganization(AdmAccountOrganizationDto organization) {
            this.organization = organization;
        }

        public AdmAccountPersonDto getPerson() {
            return person;
        }

        public void setPerson(AdmAccountPersonDto person) {
            this.person = person;
        }

        public Long getOrganizationResponsibleId() {
            return organizationResponsibleId;
        }

        public void setOrganizationResponsibleId(Long organizationResponsibleId) {
            this.organizationResponsibleId = organizationResponsibleId;
        }
    }

}
