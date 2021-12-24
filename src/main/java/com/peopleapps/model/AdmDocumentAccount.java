package com.peopleapps.model;


import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_document_account")
@SequenceGenerator(
        name = "admDocumentAccountSequence",
        sequenceName = "adm_document_account_sequence",
        initialValue = 1,
        allocationSize = 1
)
@Schema(name = "AdmDocumentAccount")
public class AdmDocumentAccount implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admDocumentAccountSequence")
    @Column(name = "document_account_id")
    private Long documentAccountId;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    public AdmDocumentAccount() {
    }

    public AdmDocumentAccount(@NotNull Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public AdmDocumentAccount(@NotNull Long documentAccountId, @NotNull LocalDateTime dateCreated) {
        this.documentAccountId = documentAccountId;
        this.dateCreated = dateCreated;
    }

    public Long getDocumentAccountId() {
        return documentAccountId;
    }

    public void setDocumentAccountId(Long documentAccountId) {
        this.documentAccountId = documentAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "AdmDocumentAccount{" +
                "documentAccountId=" + documentAccountId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
