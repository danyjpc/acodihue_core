package com.peopleapps.dto.credit;

import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorDto;
import com.peopleapps.dto.multiAccount.AdmActivityAccountDto;
import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
import com.peopleapps.dto.multiAccount.AdmReferenceAccountDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"creditKey", "creditId", "noYear", "noReference", "addressAccount", "referenceAccount",
        "documentAccount", "createdBy", "dateCreated", "status", "relationship", "partnerAccount",
        "activityAccount", "internalCode", "deadLine", "deadLineDate", "paymentType",
        "creditType", "estateDate", "statusOperated", "operatedBy", "annotation", "profesion", "occupation", "ownHouse",
        "calculator", "organization", "profession", "maxPaymentDate", "pendingBalance", "totalBalance"})
public class AdmCreditDto {
    private Long creditId;
    private Long noYear;
    private Long noReference;
    private AdmAddressAccountDto addressAccount;
    private AdmReferenceAccountDto referenceAccount;
    private AdmDocumentAccountDto documentAccount;
    private AdmPersonCreditDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmTypologyCreditDto status;
    //TODO check future relationships
    private AdmActivityAccountDto activityAccount;
    private String internalCode;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime estateDate;
    private AdmTypologyCreditDto statusOperated;
    private AdmPersonCreditDto operatedBy;
    private String annotation;
    //private AdmOrganizationResponsibleCreditDto organizationResponsible;
    private AdmOrganizationCreditDto organization;
    private AdmTypologyCreditDto profession;
    private AdmTypologyCreditDto occupation;
    private Boolean ownHouse;
    private AdmCreditCalculatorDto calculator;
    private UUID creditKey;
    //extra properties from https://app.moqups.com/0JlbBM2f8J/view/page/ac966d648
    private LocalDate maxPaymentDate;
    private BigDecimal pendingBalance;
    private BigDecimal totalBalance;

    public AdmCreditDto() {
    }

    public AdmCreditDto(Long creditId, Long noYear, Long noReference, AdmAddressAccountDto addressAccount, AdmReferenceAccountDto referenceAccount, AdmDocumentAccountDto documentAccount, AdmPersonCreditDto createdBy, LocalDateTime dateCreated, AdmTypologyCreditDto status, AdmActivityAccountDto activityAccount, String internalCode, LocalDateTime estateDate, AdmTypologyCreditDto statusOperated, AdmPersonCreditDto operatedBy, String annotation, AdmOrganizationCreditDto organization, AdmTypologyCreditDto profession, AdmTypologyCreditDto occupation, Boolean ownHouse, AdmCreditCalculatorDto calculator, UUID creditKey) {
        this.creditId = creditId;
        this.noYear = noYear;
        this.noReference = noReference;
        this.addressAccount = addressAccount;
        this.referenceAccount = referenceAccount;
        this.documentAccount = documentAccount;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.activityAccount = activityAccount;
        this.internalCode = internalCode;
        this.estateDate = estateDate;
        this.statusOperated = statusOperated;
        this.operatedBy = operatedBy;
        this.annotation = annotation;
        this.organization = organization;
        this.profession = profession;
        this.occupation = occupation;
        this.ownHouse = ownHouse;
        this.calculator = calculator;
        this.creditKey = creditKey;
    }

    public AdmCreditDto(AdmCreditCalculatorDto calculator, UUID creditKey) {
        this.calculator = calculator;
        this.creditKey = creditKey;
    }

    //constructor for global search


    public AdmCreditDto(Long creditId, Long noYear, Long noReference, AdmAddressAccountDto addressAccount, AdmReferenceAccountDto referenceAccount, AdmDocumentAccountDto documentAccount, AdmPersonCreditDto createdBy, LocalDateTime dateCreated, AdmTypologyCreditDto status, AdmActivityAccountDto activityAccount, String internalCode, LocalDateTime estateDate, AdmTypologyCreditDto statusOperated, AdmPersonCreditDto operatedBy, String annotation, AdmOrganizationCreditDto organization, AdmTypologyCreditDto profession, AdmTypologyCreditDto occupation, Boolean ownHouse, AdmCreditCalculatorDto calculator, UUID creditKey, LocalDate maxPaymentDate, BigDecimal pendingBalance, BigDecimal totalBalance) {
        this.creditId = creditId;
        this.noYear = noYear;
        this.noReference = noReference;
        this.addressAccount = addressAccount;
        this.referenceAccount = referenceAccount;
        this.documentAccount = documentAccount;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.activityAccount = activityAccount;
        this.internalCode = internalCode;
        this.estateDate = estateDate;
        this.statusOperated = statusOperated;
        this.operatedBy = operatedBy;
        this.annotation = annotation;
        this.organization = organization;
        this.profession = profession;
        this.occupation = occupation;
        this.ownHouse = ownHouse;
        this.calculator = calculator;
        this.creditKey = creditKey;
        this.maxPaymentDate = maxPaymentDate;
        this.pendingBalance = pendingBalance;
        this.totalBalance = totalBalance;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public Long getNoYear() {
        return noYear;
    }

    public void setNoYear(Long noYear) {
        this.noYear = noYear;
    }

    public Long getNoReference() {
        return noReference;
    }

    public void setNoReference(Long noReference) {
        this.noReference = noReference;
    }

    public AdmAddressAccountDto getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccountDto addressAccount) {
        this.addressAccount = addressAccount;
    }

    public AdmReferenceAccountDto getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(AdmReferenceAccountDto referenceAccount) {
        this.referenceAccount = referenceAccount;
    }

    public AdmDocumentAccountDto getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccountDto documentAccount) {
        this.documentAccount = documentAccount;
    }

    public AdmPersonCreditDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmPersonCreditDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypologyCreditDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyCreditDto status) {
        this.status = status;
    }

    public AdmActivityAccountDto getActivityAccount() {
        return activityAccount;
    }

    public void setActivityAccount(AdmActivityAccountDto activityAccount) {
        this.activityAccount = activityAccount;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public LocalDateTime getEstateDate() {
        return estateDate;
    }

    public void setEstateDate(LocalDateTime estateDate) {
        this.estateDate = estateDate;
    }

    public AdmTypologyCreditDto getStatusOperated() {
        return statusOperated;
    }

    public void setStatusOperated(AdmTypologyCreditDto statusOperated) {
        this.statusOperated = statusOperated;
    }

    public AdmPersonCreditDto getOperatedBy() {
        return operatedBy;
    }

    public void setOperatedBy(AdmPersonCreditDto operatedBy) {
        this.operatedBy = operatedBy;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public AdmOrganizationCreditDto getOrganization() {
        return organization;
    }

    public void setOrganization(AdmOrganizationCreditDto organization) {
        this.organization = organization;
    }

    public AdmTypologyCreditDto getProfession() {
        return profession;
    }

    public void setProfession(AdmTypologyCreditDto profession) {
        this.profession = profession;
    }

    public AdmTypologyCreditDto getOccupation() {
        return occupation;
    }

    public void setOccupation(AdmTypologyCreditDto occupation) {
        this.occupation = occupation;
    }

    public Boolean getOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(Boolean ownHouse) {
        this.ownHouse = ownHouse;
    }

    public AdmCreditCalculatorDto getCalculator() {
        return calculator;
    }

    public UUID getCreditKey() {
        return creditKey;
    }

    public void setCreditKey(UUID creditKey) {
        this.creditKey = creditKey;
    }

    public void setCalculator(AdmCreditCalculatorDto calculator) {
        this.calculator = calculator;
    }

    public LocalDate getMaxPaymentDate() {
        return maxPaymentDate;
    }

    public void setMaxPaymentDate(LocalDate maxPaymentDate) {
        this.maxPaymentDate = maxPaymentDate;
    }

    public BigDecimal getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(BigDecimal pendingBalance) {
        this.pendingBalance = pendingBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public static class AdmPersonCreditDto {
        private UUID personKey;
        private String email;
        private String firstName;
        private String lastName;
        private String nameComplete;

        public AdmPersonCreditDto() {
        }

        public AdmPersonCreditDto(UUID personKey, String email, String firstName, String lastName, String nameComplete) {
            this.personKey = personKey;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.nameComplete = nameComplete;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getNameComplete() {
            return nameComplete;
        }

        public void setNameComplete(String nameComplete) {
            this.nameComplete = nameComplete;
        }
    }

    public static class AdmTypologyCreditDto {
        private Long typologyId;
        private String description;

        public AdmTypologyCreditDto() {
        }

        public AdmTypologyCreditDto(Long typologyId, String description) {
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

    public static class AdmOrganizationResponsibleCreditDto {
        private Long organizationResponsibleId;
        private AdmOrganizationCreditDto organization;
        private AdmPersonCreditDto person;

        public AdmOrganizationResponsibleCreditDto() {
        }

        public AdmOrganizationResponsibleCreditDto(Long organizationResponsibleId, AdmOrganizationCreditDto organization, AdmPersonCreditDto person) {
            this.organizationResponsibleId = organizationResponsibleId;
            this.organization = organization;
            this.person = person;
        }

        public Long getOrganizationResponsibleId() {
            return organizationResponsibleId;
        }

        public void setOrganizationResponsibleId(Long organizationResponsibleId) {
            this.organizationResponsibleId = organizationResponsibleId;
        }

        public AdmOrganizationCreditDto getOrganization() {
            return organization;
        }

        public void setOrganization(AdmOrganizationCreditDto organization) {
            this.organization = organization;
        }

        public AdmPersonCreditDto getPerson() {
            return person;
        }

        public void setPerson(AdmPersonCreditDto person) {
            this.person = person;
        }

    }

    public static class AdmOrganizationCreditDto {
        private UUID organizationKey;
        private String organizationName;
        private String organizationCommercial;

        public AdmOrganizationCreditDto() {
        }

        public AdmOrganizationCreditDto(UUID organizationKey, String organizationName, String organizationCommercial) {
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


}
