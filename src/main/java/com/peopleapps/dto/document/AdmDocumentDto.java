package com.peopleapps.dto.document;

import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(name = "AdmDocument")
@JsonbPropertyOrder({"documentId", "path", "dateCreated", "leader", "documentName", "documentAccount", "createdBy",
        "status", "documentCreditType"})
public class AdmDocumentDto {
    private Long documentId;
    private AdmDocDocumentAccountDto documentAccount;
    private String path;
    private AdmDocumentPersonDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmDocumentTypologyDto status;
    private Boolean leader;
    private AdmDocumentTypologyDto documentCreditType;
    private String documentName;

    public AdmDocumentDto(Long documentId, AdmDocDocumentAccountDto documentAccount, String path, AdmDocumentPersonDto createdBy, LocalDateTime dateCreated, AdmDocumentTypologyDto status, Boolean leader, AdmDocumentTypologyDto documentCreditType, String documentName) {
        this.documentId = documentId;
        this.documentAccount = documentAccount;
        this.path = path;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.leader = leader;
        this.documentCreditType = documentCreditType;
        this.documentName = documentName;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public AdmDocDocumentAccountDto getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocDocumentAccountDto documentAccount) {
        this.documentAccount = documentAccount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AdmDocumentPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmDocumentPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmDocumentTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmDocumentTypologyDto status) {
        this.status = status;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public AdmDocumentTypologyDto getDocumentCreditType() {
        return documentCreditType;
    }

    public void setDocumentCreditType(AdmDocumentTypologyDto documentCreditType) {
        this.documentCreditType = documentCreditType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public static class AdmDocDocumentAccountDto {
        private Long documentAccountId;

        public AdmDocDocumentAccountDto(Long documentAccountId) {
            this.documentAccountId = documentAccountId;
        }

        public Long getDocumentAccountId() {
            return documentAccountId;
        }

        public void setDocumentAccountId(Long documentAccountId) {
            this.documentAccountId = documentAccountId;
        }
    }

    public static class AdmDocumentTypologyDto {
        private Long typologyId;
        private String description;

        public AdmDocumentTypologyDto() {
        }

        public AdmDocumentTypologyDto(Long typologyId) {
            this.typologyId = typologyId;
        }

        public AdmDocumentTypologyDto(Long typologyId, String description) {
            this.typologyId = typologyId;
            this.description = description;
        }

        public Long getTypologyId() {
            return typologyId;
        }

        public void setTypologyId(Long typologyId) {
            this.typologyId = typologyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class AdmDocumentPersonDto {
        private UUID personKey;
        private String firstName;
        private String lastName;
        @JsonbDateFormat(value = CsnConstants.ONLY_DATE_FORMAT)
        private LocalDate birthday;
        private String email;
        private String nameComplete;

        public AdmDocumentPersonDto() {
        }

        public AdmDocumentPersonDto(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public AdmDocumentPersonDto(UUID personKey, String firstName, String lastName, LocalDate birthday, String email, String nameComplete) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.email = email;
            this.nameComplete = nameComplete;
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

        public String getNameComplete() {
            return nameComplete;
        }

        public void setNameComplete(String nameComplete) {
            this.nameComplete = nameComplete;
        }
    }
}
