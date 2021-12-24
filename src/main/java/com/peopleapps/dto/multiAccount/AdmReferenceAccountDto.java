package com.peopleapps.dto.multiAccount;

public class AdmReferenceAccountDto {
    private Long referenceAccountId;

    public AdmReferenceAccountDto() {
    }

    public AdmReferenceAccountDto(Long referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    public Long getReferenceAccountId() {
        return referenceAccountId;
    }

    public void setReferenceAccountId(Long referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }
}
