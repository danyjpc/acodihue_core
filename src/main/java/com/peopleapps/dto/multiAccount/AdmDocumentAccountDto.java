package com.peopleapps.dto.multiAccount;

public class AdmDocumentAccountDto {
    private Long documentAccountId;

    public AdmDocumentAccountDto() {
    }

    public AdmDocumentAccountDto(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public Long getDocumentAccountId() {
        return documentAccountId;
    }

    public void setDocumentAccountId(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }
}
