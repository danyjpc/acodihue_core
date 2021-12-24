package com.peopleapps.dto.multiAccount;

public class AdmBeneficiaryAccountDto {
    private Long beneficiaryAccountId;

    public AdmBeneficiaryAccountDto() {
    }

    public AdmBeneficiaryAccountDto(Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }

    public Long getBeneficiaryAccountId() {
        return beneficiaryAccountId;
    }

    public void setBeneficiaryAccountId(Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }
}
