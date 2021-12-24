package com.peopleapps.dto.globalSearch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmGlobalSearchListDto {
    private UUID personKey;
    private Long phoneAccountId;
    private Long documentAccountId;
    private Long addressAccountId;
    private Long beneficiaryAccountId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String partnerName;
    private String marriedName;
    private LocalDate birthday;
    private String email;
    private Long maritalStatusId;
    private String maritalStatusDesc;
    //TODO add if person.profession is changed to typology
    private Long professionId;
    private String professionDesc;
    //private String profession;
    private Long cui;
    private Long documentTypeId;
    private String documentTypeDesc;
    private Long documentOrderId;
    private String documentOrderDesc;
    private Long orderNumber;
    private String nit;
    private Long countryOfBirthId;
    private String countryOfBirthDesc;
    private Long stateOfBirthId;
    private String stateOfBirthDesc;
    private Long cityOfBirthId;
    private String cityOfBirthDesc;
    private Long immigrationConditionId;
    private String immigrationConditionDesc;
    private Long genreId;
    private String genreDesc;
    private String passport;
    private Boolean ownAccount;
    private Boolean ownAccountDescription;
    private Long mayanPeopleId;
    private String mayanPeopleDesc;
    private Long roleId;
    private String roleDesc;
    private Long statusId;
    private String statusDesc;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Boolean isPartner;
    private Boolean isBeneficiary;
    private Long membershipNumber;
    //New properties are added from this point on
    private String nameComplete;
    private Boolean isAssociate;
    private Long linguisticCommunityId;
    private String linguisticCommunityDesc;

    public AdmGlobalSearchListDto(UUID personKey, Long phoneAccountId, Long documentAccountId, Long addressAccountId, Long beneficiaryAccountId, String firstName, String middleName, String lastName, String partnerName, String marriedName, LocalDate birthday, String email, Long maritalStatusId, String maritalStatusDesc, Long professionId, String professionDesc, Long cui, Long documentTypeId, String documentTypeDesc, Long documentOrderId, String documentOrderDesc, Long orderNumber, String nit, Long countryOfBirthId, String countryOfBirthDesc, Long stateOfBirthId, String stateOfBirthDesc, Long cityOfBirthId, String cityOfBirthDesc, Long immigrationConditionId, String immigrationConditionDesc, Long genreId, String genreDesc, String passport, Boolean ownAccount, Boolean ownAccountDescription, Long mayanPeopleId, String mayanPeopleDesc, Long roleId, String roleDesc, Long statusId, String statusDesc, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Boolean isPartner, Boolean isBeneficiary, Long membershipNumber, String nameComplete, Boolean isAssociate) {
        this.personKey = personKey;
        this.phoneAccountId = phoneAccountId;
        this.documentAccountId = documentAccountId;
        this.addressAccountId = addressAccountId;
        this.beneficiaryAccountId = beneficiaryAccountId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.partnerName = partnerName;
        this.marriedName = marriedName;
        this.birthday = birthday;
        this.email = email;
        this.maritalStatusId = maritalStatusId;
        this.maritalStatusDesc = maritalStatusDesc;
        this.professionId = professionId;
        this.professionDesc = professionDesc;
        this.cui = cui;
        this.documentTypeId = documentTypeId;
        this.documentTypeDesc = documentTypeDesc;
        this.documentOrderId = documentOrderId;
        this.documentOrderDesc = documentOrderDesc;
        this.orderNumber = orderNumber;
        this.nit = nit;
        this.countryOfBirthId = countryOfBirthId;
        this.countryOfBirthDesc = countryOfBirthDesc;
        this.stateOfBirthId = stateOfBirthId;
        this.stateOfBirthDesc = stateOfBirthDesc;
        this.cityOfBirthId = cityOfBirthId;
        this.cityOfBirthDesc = cityOfBirthDesc;
        this.immigrationConditionId = immigrationConditionId;
        this.immigrationConditionDesc = immigrationConditionDesc;
        this.genreId = genreId;
        this.genreDesc = genreDesc;
        this.passport = passport;
        this.ownAccount = ownAccount;
        this.ownAccountDescription = ownAccountDescription;
        this.mayanPeopleId = mayanPeopleId;
        this.mayanPeopleDesc = mayanPeopleDesc;
        this.roleId = roleId;
        this.roleDesc = roleDesc;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.isPartner = isPartner;
        this.isBeneficiary = isBeneficiary;
        this.membershipNumber = membershipNumber;
        this.nameComplete = nameComplete;
        this.isAssociate = isAssociate;
    }

    public AdmGlobalSearchListDto(UUID personKey, String firstName, String middleName, String lastName, String partnerName, String marriedName, String email, Long cui, String nit, Long statusId, String statusDesc, LocalDateTime dateCreated, Long membershipNumber, String nameComplete,  Long linguisticCommunityId, String linguisticCommunityDesc) {
        this.personKey = personKey;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.partnerName = partnerName;
        this.marriedName = marriedName;
        this.email = email;
        this.cui = cui;
        this.nit = nit;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.dateCreated = dateCreated;
        this.membershipNumber = membershipNumber;
        this.nameComplete = nameComplete;
        this.linguisticCommunityId = linguisticCommunityId;
        this.linguisticCommunityDesc = linguisticCommunityDesc;
    }

    public UUID getPersonKey() {
        return personKey;
    }

    public void setPersonKey(UUID personKey) {
        this.personKey = personKey;
    }

    public Long getPhoneAccountId() {
        return phoneAccountId;
    }

    public void setPhoneAccountId(Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
    }

    public Long getDocumentAccountId() {
        return documentAccountId;
    }

    public void setDocumentAccountId(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public Long getAddressAccountId() {
        return addressAccountId;
    }

    public void setAddressAccountId(Long addressAccountId) {
        this.addressAccountId = addressAccountId;
    }

    public Long getBeneficiaryAccountId() {
        return beneficiaryAccountId;
    }

    public void setBeneficiaryAccountId(Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
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

    public Long getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(Long maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public String getMaritalStatusDesc() {
        return maritalStatusDesc;
    }

    public void setMaritalStatusDesc(String maritalStatusDesc) {
        this.maritalStatusDesc = maritalStatusDesc;
    }

    public Long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Long professionId) {
        this.professionId = professionId;
    }

    public String getProfessionDesc() {
        return professionDesc;
    }

    public void setProfessionDesc(String professionDesc) {
        this.professionDesc = professionDesc;
    }

    public Long getCui() {
        return cui;
    }

    public void setCui(Long cui) {
        this.cui = cui;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getDocumentTypeDesc() {
        return documentTypeDesc;
    }

    public void setDocumentTypeDesc(String documentTypeDesc) {
        this.documentTypeDesc = documentTypeDesc;
    }

    public Long getDocumentOrderId() {
        return documentOrderId;
    }

    public void setDocumentOrderId(Long documentOrderId) {
        this.documentOrderId = documentOrderId;
    }

    public String getDocumentOrderDesc() {
        return documentOrderDesc;
    }

    public void setDocumentOrderDesc(String documentOrderDesc) {
        this.documentOrderDesc = documentOrderDesc;
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

    public Long getCountryOfBirthId() {
        return countryOfBirthId;
    }

    public void setCountryOfBirthId(Long countryOfBirthId) {
        this.countryOfBirthId = countryOfBirthId;
    }

    public String getCountryOfBirthDesc() {
        return countryOfBirthDesc;
    }

    public void setCountryOfBirthDesc(String countryOfBirthDesc) {
        this.countryOfBirthDesc = countryOfBirthDesc;
    }

    public Long getStateOfBirthId() {
        return stateOfBirthId;
    }

    public void setStateOfBirthId(Long stateOfBirthId) {
        this.stateOfBirthId = stateOfBirthId;
    }

    public String getStateOfBirthDesc() {
        return stateOfBirthDesc;
    }

    public void setStateOfBirthDesc(String stateOfBirthDesc) {
        this.stateOfBirthDesc = stateOfBirthDesc;
    }

    public Long getCityOfBirthId() {
        return cityOfBirthId;
    }

    public void setCityOfBirthId(Long cityOfBirthId) {
        this.cityOfBirthId = cityOfBirthId;
    }

    public String getCityOfBirthDesc() {
        return cityOfBirthDesc;
    }

    public void setCityOfBirthDesc(String cityOfBirthDesc) {
        this.cityOfBirthDesc = cityOfBirthDesc;
    }

    public Long getImmigrationConditionId() {
        return immigrationConditionId;
    }

    public void setImmigrationConditionId(Long immigrationConditionId) {
        this.immigrationConditionId = immigrationConditionId;
    }

    public String getImmigrationConditionDesc() {
        return immigrationConditionDesc;
    }

    public void setImmigrationConditionDesc(String immigrationConditionDesc) {
        this.immigrationConditionDesc = immigrationConditionDesc;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreDesc() {
        return genreDesc;
    }

    public void setGenreDesc(String genreDesc) {
        this.genreDesc = genreDesc;
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

    public Long getMayanPeopleId() {
        return mayanPeopleId;
    }

    public void setMayanPeopleId(Long mayanPeopleId) {
        this.mayanPeopleId = mayanPeopleId;
    }

    public String getMayanPeopleDesc() {
        return mayanPeopleDesc;
    }

    public void setMayanPeopleDesc(String mayanPeopleDesc) {
        this.mayanPeopleDesc = mayanPeopleDesc;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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

    public Boolean getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Boolean partner) {
        isPartner = partner;
    }

    public Boolean getIsBeneficiary() {
        return isBeneficiary;
    }

    public void setIsBeneficiary(Boolean beneficiary) {
        isBeneficiary = beneficiary;
    }

    public Long getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(Long membershipNumber) {
        this.membershipNumber = membershipNumber;
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

    public Long getLinguisticCommunityId() {
        return linguisticCommunityId;
    }

    public void setLinguisticCommunityId(Long linguisticCommunityId) {
        this.linguisticCommunityId = linguisticCommunityId;
    }

    public String getLinguisticCommunityDesc() {
        return linguisticCommunityDesc;
    }

    public void setLinguisticCommunityDesc(String linguisticCommunityDesc) {
        this.linguisticCommunityDesc = linguisticCommunityDesc;
    }
}
