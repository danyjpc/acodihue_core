package com.peopleapps.dto.globalSearch;


import java.time.LocalDateTime;
import java.util.UUID;

public class AdmGlobalSearchAccountListDto {
    private Long accountId;
    private Long organizationResponsibleId;
    private UUID organizationKey;
    private String organizationName;
    private String organizationCommercial;
    private UUID personKey;
    private String firstName;
    private String lastName;
    private Long accountTypeId;
    private String accountTypeDesc;
    private Long numAccount;
    private Long numAccountOrder;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDesc;
    private Double balance;
    private String accountTypeValue2;

    public AdmGlobalSearchAccountListDto() {
    }

    public AdmGlobalSearchAccountListDto(Long accountId, Long organizationResponsibleId, UUID organizationKey, String organizationName, String organizationCommercial, UUID personKey, String firstName, String lastName, Long accountTypeId, String accountTypeDesc, Long numAccount, Long numAccountOrder, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDesc, Double balance, String accountTypeValue2) {
        this.accountId = accountId;
        this.organizationResponsibleId = organizationResponsibleId;
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.personKey = personKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountTypeId = accountTypeId;
        this.accountTypeDesc = accountTypeDesc;
        this.numAccount = numAccount;
        this.numAccountOrder = numAccountOrder;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.balance = balance;
        this.accountTypeValue2 = accountTypeValue2;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOrganizationResponsibleId() {
        return organizationResponsibleId;
    }

    public void setOrganizationResponsibleId(Long organizationResponsibleId) {
        this.organizationResponsibleId = organizationResponsibleId;
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

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeDesc() {
        return accountTypeDesc;
    }

    public void setAccountTypeDesc(String accountTypeDesc) {
        this.accountTypeDesc = accountTypeDesc;
    }

    public Long getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(Long numAccount) {
        this.numAccount = numAccount;
    }

    public Long getNumAccountOrder() {
        return numAccountOrder;
    }

    public void setNumAccountOrder(Long numAccountOrder) {
        this.numAccountOrder = numAccountOrder;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountTypeValue2() {
        return accountTypeValue2;
    }

    public void setAccountTypeValue2(String accountTypeValue2) {
        this.accountTypeValue2 = accountTypeValue2;
    }
}
