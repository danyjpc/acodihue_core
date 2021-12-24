package com.peopleapps.dto.multiAccount;

public class AdmAddressAccountDto {
    private Long addressAccountId;

    public AdmAddressAccountDto() {
    }

    public AdmAddressAccountDto(Long addressAccountId) {
        this.addressAccountId = addressAccountId;
    }

    public Long getAddressAccountId() {
        return addressAccountId;
    }

    public void setAddressAccountId(Long addressAccountId) {
        this.addressAccountId = addressAccountId;
    }
}
