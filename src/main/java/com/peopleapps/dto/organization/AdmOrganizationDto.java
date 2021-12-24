package com.peopleapps.dto.organization;

import java.math.BigDecimal;
import java.util.UUID;

public class AdmOrganizationDto {
    private UUID organizationKey;
    private String organizationName;
    private String organizationCommercial;
    private BigDecimal interestRate;

    public AdmOrganizationDto() {
    }

    public AdmOrganizationDto(UUID organizationKey, String organizationName, String organizationCommercial, BigDecimal interestRate) {
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.interestRate = interestRate;
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}