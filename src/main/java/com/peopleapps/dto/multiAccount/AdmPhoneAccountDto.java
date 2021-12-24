package com.peopleapps.dto.multiAccount;

public class AdmPhoneAccountDto {
    private Long phoneAccountId;

    public AdmPhoneAccountDto() {
    }

    public AdmPhoneAccountDto(Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
    }

    public Long getPhoneAccountId() {
        return phoneAccountId;
    }

    public void setPhoneAccountId(Long phoneAccountId) {
        this.phoneAccountId = phoneAccountId;
    }
}
