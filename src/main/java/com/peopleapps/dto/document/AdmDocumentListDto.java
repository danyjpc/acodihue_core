package com.peopleapps.dto.document;

import com.peopleapps.model.AdmDocumentAccount;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.model.AdmUser;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmDocumentListDto {
    private Long documentId;
    private Long documentAccountId;
    private String path;
    private String createdByEmail;
    private UUID createdByPersonKey;
    private LocalDateTime dateCreated;
    private Long statusId;
    private String statusDesc;
    private Boolean leader;
    private Long documentCreditTypeId;
    private String documentCreditTypeDesc;
    private String documentName;

    public AdmDocumentListDto() {
    }

    public AdmDocumentListDto(Long documentId, Long documentAccountId, String path, String createdByEmail, UUID createdByPersonKey, LocalDateTime dateCreated, Long statusId, String statusDesc, Boolean leader, Long documentCreditTypeId, String documentCreditTypeDesc, String documentName) {
        this.documentId = documentId;
        this.documentAccountId = documentAccountId;
        this.path = path;
        this.createdByEmail = createdByEmail;
        this.createdByPersonKey = createdByPersonKey;
        this.dateCreated = dateCreated;
        this.statusId = statusId;
        this.statusDesc = statusDesc;
        this.leader = leader;
        this.documentCreditTypeId = documentCreditTypeId;
        this.documentCreditTypeDesc = documentCreditTypeDesc;
        this.documentName = documentName;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentAccountId() {
        return documentAccountId;
    }

    public void setDocumentAccountId(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public Long getDocumentCreditTypeId() {
        return documentCreditTypeId;
    }

    public void setDocumentCreditTypeId(Long documentCreditTypeId) {
        this.documentCreditTypeId = documentCreditTypeId;
    }

    public String getDocumentCreditTypeDesc() {
        return documentCreditTypeDesc;
    }

    public void setDocumentCreditTypeDesc(String documentCreditTypeDesc) {
        this.documentCreditTypeDesc = documentCreditTypeDesc;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
