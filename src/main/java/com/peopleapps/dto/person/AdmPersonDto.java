package com.peopleapps.dto.person;

import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmBeneficiaryAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
import com.peopleapps.dto.multiAccount.AdmPhoneAccountDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"personKey", "phoneAccount", "documentAccount", "addressAccount", "beneficiaryAccount",
        "firstName", "middleName", "lastName", "partnerName", "marriedName",
        "birthday", "email", "maritalStatus", "profession", "cui",
        "documentType", "documentOrder", "orderNumber", "nit", "countryOfBirth",
        "stateOfBirth", "cityOfBirth", "immigrationCondition", "genre", "passport",
        "ownAccount", "ownAccountDescription", "mayanPeople", "role", "status",
        "createdBy", "dateCreated", "isPartner", "isBeneficiary", "membershipNumber"})
public class AdmPersonDto {
    private UUID personKey;
    private AdmPhoneAccountDto phoneAccount;
    private AdmDocumentAccountDto documentAccount;
    private AdmAddressAccountDto addressAccount;
    private AdmBeneficiaryAccountDto beneficiaryAccount;
    private String firstName;
    private String middleName;
    private String lastName;
    private String partnerName;
    private String marriedName;
    @JsonbDateFormat(value = CsnConstants.ONLY_DATE_FORMAT)
    private LocalDate birthday;
    private String email;
    private AdmTypologyDto maritalStatus;
    //TODO add if person.profession is changed to typology
    private AdmTypologyDto profession;
    //private String profession;
    private Long cui;
    private AdmTypologyDto documentType;
    private AdmTypologyDto documentOrder;
    private Long orderNumber;
    private String nit;
    private AdmTypologyDto countryOfBirth;
    private AdmTypologyDto stateOfBirth;
    private AdmTypologyDto cityOfBirth;
    private AdmTypologyDto immigrationCondition;
    private AdmTypologyDto genre;
    private String passport;
    private Boolean ownAccount;
    private Boolean ownAccountDescription;
    private AdmTypologyDto mayanPeople;
    private AdmTypologyDto role;
    private AdmTypologyDto status;
    private AdmUserInfoDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private Boolean isPartner;
    private Boolean isBeneficiary;
    //value is long on database but a format is needed for displaying it (000 prefix)
    private String membershipNumber;
    private String nameComplete;
    private Boolean isAssociate;
    private AdmTypologyDto linguisticCommunity;

    public AdmPersonDto() {
    }

    public AdmPersonDto(UUID personKey, AdmPhoneAccountDto phoneAccount, AdmDocumentAccountDto documentAccount, AdmAddressAccountDto addressAccount, AdmBeneficiaryAccountDto beneficiaryAccount, String firstName, String middleName, String lastName, String partnerName, String marriedName, LocalDate birthday, String email, AdmTypologyDto maritalStatus, AdmTypologyDto profession, Long cui, AdmTypologyDto documentType, AdmTypologyDto documentOrder, Long orderNumber, String nit, AdmTypologyDto countryOfBirth, AdmTypologyDto stateOfBirth, AdmTypologyDto cityOfBirth, AdmTypologyDto immigrationCondition, AdmTypologyDto genre, String passport, Boolean ownAccount, Boolean ownAccountDescription, AdmTypologyDto mayanPeople, AdmTypologyDto role, AdmTypologyDto status, AdmUserInfoDto createdBy, LocalDateTime dateCreated, Boolean isPartner, Boolean isBeneficiary, String membershipNumber, String nameComplete, Boolean isAssociate, AdmTypologyDto linguisticCommunity) {
        this.personKey = personKey;
        this.phoneAccount = phoneAccount;
        this.documentAccount = documentAccount;
        this.addressAccount = addressAccount;
        this.beneficiaryAccount = beneficiaryAccount;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.partnerName = partnerName;
        this.marriedName = marriedName;
        this.birthday = birthday;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.profession = profession;
        this.cui = cui;
        this.documentType = documentType;
        this.documentOrder = documentOrder;
        this.orderNumber = orderNumber;
        this.nit = nit;
        this.countryOfBirth = countryOfBirth;
        this.stateOfBirth = stateOfBirth;
        this.cityOfBirth = cityOfBirth;
        this.immigrationCondition = immigrationCondition;
        this.genre = genre;
        this.passport = passport;
        this.ownAccount = ownAccount;
        this.ownAccountDescription = ownAccountDescription;
        this.mayanPeople = mayanPeople;
        this.role = role;
        this.status = status;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.isPartner = isPartner;
        this.isBeneficiary = isBeneficiary;
        this.membershipNumber = membershipNumber;
        this.nameComplete = nameComplete;
        this.isAssociate = isAssociate;
        this.linguisticCommunity = linguisticCommunity;
    }

    public AdmPersonDto(UUID personKey, String firstName, String middleName, String lastName, String partnerName, String marriedName, String email, Long cui, String nit, AdmTypologyDto status, LocalDateTime dateCreated, String membershipNumber, String nameComplete, AdmTypologyDto linguisticCommunity) {
        this.personKey = personKey;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.partnerName = partnerName;
        this.marriedName = marriedName;
        this.email = email;
        this.cui = cui;
        this.nit = nit;
        this.status = status;
        this.dateCreated = dateCreated;
        this.membershipNumber = membershipNumber;
        this.nameComplete = nameComplete;
        this.linguisticCommunity = linguisticCommunity;
    }

    public AdmPersonDto(UUID personKey, String firstName, String middleName, String lastName, String email, String nameComplete, LocalDate birthday) {
        this.personKey = personKey;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.nameComplete = nameComplete;
        this.birthday = birthday;
    }

    public AdmPersonDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AdmPersonDto(UUID personKey, String nameComplete) {
        this.personKey = personKey;
        this.nameComplete = nameComplete;
    }


    public AdmPersonDto(UUID personKey, String firstName, String lastName, String membershipNumber) {
        this.personKey = personKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipNumber = membershipNumber;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    public AdmPhoneAccountDto getPhoneAccount() {
        return phoneAccount;
    }

    public void setPhoneAccount(AdmPhoneAccountDto phoneAccount) {
        this.phoneAccount = phoneAccount;
    }

    public AdmDocumentAccountDto getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccountDto documentAccount) {
        this.documentAccount = documentAccount;
    }

    public AdmAddressAccountDto getAddressAccount() {
        return addressAccount;
    }

    public void setAddressAccount(AdmAddressAccountDto addressAccount) {
        this.addressAccount = addressAccount;
    }

    public AdmBeneficiaryAccountDto getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(AdmBeneficiaryAccountDto beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMarriedName() {
        return marriedName;
    }

    public void setMarriedName(String marriedName) {
        this.marriedName = marriedName;
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

    public AdmTypologyDto getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(AdmTypologyDto maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public AdmTypologyDto getProfession() {
        return profession;
    }

    public void setProfession(AdmTypologyDto profession) {
        this.profession = profession;
    }

    public Long getCui() {
        return cui;
    }

    public void setCui(Long cui) {
        this.cui = cui;
    }

    public AdmTypologyDto getDocumentType() {
        return documentType;
    }

    public void setDocumentType(AdmTypologyDto documentType) {
        this.documentType = documentType;
    }

    public AdmTypologyDto getDocumentOrder() {
        return documentOrder;
    }

    public void setDocumentOrder(AdmTypologyDto documentOrder) {
        this.documentOrder = documentOrder;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public AdmTypologyDto getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(AdmTypologyDto countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public AdmTypologyDto getStateOfBirth() {
        return stateOfBirth;
    }

    public void setStateOfBirth(AdmTypologyDto stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
    }

    public AdmTypologyDto getCityOfBirth() {
        return cityOfBirth;
    }

    public void setCityOfBirth(AdmTypologyDto cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public AdmTypologyDto getImmigrationCondition() {
        return immigrationCondition;
    }

    public void setImmigrationCondition(AdmTypologyDto immigrationCondition) {
        this.immigrationCondition = immigrationCondition;
    }

    public AdmTypologyDto getGenre() {
        return genre;
    }

    public void setGenre(AdmTypologyDto genre) {
        this.genre = genre;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Boolean getOwnAccount() {
        return ownAccount;
    }

    public void setOwnAccount(Boolean ownAccount) {
        this.ownAccount = ownAccount;
    }

    public Boolean getOwnAccountDescription() {
        return ownAccountDescription;
    }

    public void setOwnAccountDescription(Boolean ownAccountDescription) {
        this.ownAccountDescription = ownAccountDescription;
    }

    public AdmTypologyDto getMayanPeople() {
        return mayanPeople;
    }

    public void setMayanPeople(AdmTypologyDto mayanPeople) {
        this.mayanPeople = mayanPeople;
    }

    public AdmTypologyDto getRole() {
        return role;
    }

    public void setRole(AdmTypologyDto role) {
        this.role = role;
    }

    public AdmTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyDto status) {
        this.status = status;
    }

    public AdmUserInfoDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUserInfoDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getPartner() {
        return isPartner;
    }

    public void setPartner(Boolean partner) {
        isPartner = partner;
    }

    public Boolean getBeneficiary() {
        return isBeneficiary;
    }

    public void setBeneficiary(Boolean beneficiary) {
        isBeneficiary = beneficiary;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public Boolean getAssociate() {
        return isAssociate;
    }

    public void setAssociate(Boolean associate) {
        isAssociate = associate;
    }

    public String getNameComplete() {
        return nameComplete;
    }

    public void setNameComplete(String nameComplete) {
        this.nameComplete = nameComplete;
    }

    public Boolean getIsAssociate() {
        return isAssociate;
    }

    public void setIsAssociate(Boolean associate) {
        isAssociate = associate;
    }

    public AdmTypologyDto getLinguisticCommunity() {
        return linguisticCommunity;
    }

    public void setLinguisticCommunity(AdmTypologyDto linguisticCommunity) {
        this.linguisticCommunity = linguisticCommunity;
    }
}
