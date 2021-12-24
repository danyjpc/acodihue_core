package com.peopleapps.model;

import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_reference_account")
@SequenceGenerator(
        name = "admReferenceAccountSequence",
        sequenceName = "adm_reference_account_sequence",
        initialValue = 1,
        allocationSize = 1
)
@Schema(name = "AdmReferenceAccount")
public class AdmReferenceAccount implements Serializable {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admReferenceAccountSequence")
    @Column(name = "reference_account_id")
    private Long referenceAccountId;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    public AdmReferenceAccount() {
    }

    public AdmReferenceAccount(@NotNull Long referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    public AdmReferenceAccount(@NotNull Long referenceAccountId, @NotNull LocalDateTime dateCreated) {
        this.referenceAccountId = referenceAccountId;
        this.dateCreated = dateCreated;
    }

    public Long getReferenceAccountId() {
        return referenceAccountId;
    }

    public void setReferenceAccountId(Long referenceAccountId) {
        this.referenceAccountId = referenceAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "AdmReferenceAccount{" +
                "referenceAccountId=" + referenceAccountId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
