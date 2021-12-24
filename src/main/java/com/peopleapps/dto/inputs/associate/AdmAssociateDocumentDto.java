package com.peopleapps.dto.inputs.associate;

import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAssociateDocumentDto {

    private Long documentId;
    private String path;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type;
    private AdmAssociateDocumentDto.AdmAssociateCreatedByDto createdBy;
    private Boolean leader;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status;
    private String documentName;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private String relativePath;

    public AdmAssociateDocumentDto() {

    }

    public AdmAssociateDocumentDto(Long documentId, String path, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type, AdmAssociateCreatedByDto createdBy, Boolean leader, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status, String documentName, LocalDateTime dateCreated, String relativePath) {
        this.documentId = documentId;
        this.path = path;
        this.type = type;
        this.createdBy = createdBy;
        this.leader = leader;
        this.status = status;
        this.documentName = documentName;
        this.dateCreated = dateCreated;
        this.relativePath = relativePath;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getType() {
        return type;
    }

    public void setType(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type) {
        this.type = type;
    }

    public AdmAssociateCreatedByDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmAssociateCreatedByDto createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public static class AdmAssociateCreatedByDto {
        private UUID personKey;

        public AdmAssociateCreatedByDto() {
        }

        public AdmAssociateCreatedByDto(UUID personKey) {
            this.personKey = personKey;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
