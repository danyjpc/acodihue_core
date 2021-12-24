package com.peopleapps.model;

import com.peopleapps.dto.document.AdmDocumentListDto;
import com.peopleapps.dto.user.AdmUserListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_document")
@SequenceGenerator(
        name = "admDocumentSequence",
        sequenceName = "adm_document_sequence",
        initialValue = 1,
        allocationSize = 1

)

@SqlResultSetMapping(
        name = "admDocumentListDto",
        classes = @ConstructorResult(
                targetClass = AdmDocumentListDto.class,
                columns = {
                        @ColumnResult(name = "document_id", type = Long.class),
                        @ColumnResult(name = "document_account_id", type = Long.class),
                        @ColumnResult(name = "path", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "leader", type = Boolean.class),
                        @ColumnResult(name = "document_credit_type_id", type = Long.class),
                        @ColumnResult(name = "document_credit_type_desc", type = String.class),
                        @ColumnResult(name = "document_name", type = String.class),
                }
        )
)
@Schema(name = "AdmDocument")
@JsonbPropertyOrder({"documentId", "path", "dateCreated", "leader", "documentAccount", "created_by",
        "status", "documentCreditType"})
public class AdmDocument implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admDocumentSequence")
    @NotNull
    @Column(name = "document_id")
    private Long documentId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_account_id")
    @DefaultValue("0")
    private AdmDocumentAccount documentAccount;

    @Column(name = "path")
    @NotNull
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    @NotNull
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @Column(name = "leader")
    @DefaultValue("FALSE")
    private Boolean leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_credit_type")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology documentCreditType;

    @Column(name = "document_name")
    @NotNull
    private String documentName;

    public AdmDocument() {
    }

    public AdmDocument(@NotNull Long documentId, @NotNull AdmDocumentAccount documentAccount, @NotNull String path, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status, Boolean leader, @NotNull AdmTypology documentCreditType, @NotNull String documentName) {
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

    public AdmDocumentAccount getDocumentAccount() {
        return documentAccount;
    }

    public void setDocumentAccount(AdmDocumentAccount documentAccount) {
        this.documentAccount = documentAccount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public AdmTypology getDocumentCreditType() {
        return documentCreditType;
    }

    public void setDocumentCreditType(AdmTypology documentCreditType) {
        this.documentCreditType = documentCreditType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public String toString() {
        return "AdmDocument{" +
                "documentId=" + documentId +
                ", documentAccount=" + documentAccount +
                ", path='" + path + '\'' +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", leader=" + leader +
                ", documentCreditType=" + documentCreditType +
                ", documentName='" + documentName + '\'' +
                '}';
    }
}
