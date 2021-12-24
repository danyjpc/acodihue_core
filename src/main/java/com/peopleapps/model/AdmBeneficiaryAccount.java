package com.peopleapps.model;

import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_beneficiary_account")
@SequenceGenerator(
        name = "admBeneficiaryAccountSequence",
        sequenceName = "adm_beneficiary_account_sequence",
        allocationSize = 1,
        initialValue = 1
)
@Schema(name = "AdmBeneficiaryAccount")
public class AdmBeneficiaryAccount implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admUserSequence")
    @Column(name = "beneficiary_account_id")
    private Long beneficiaryAccountId;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    public AdmBeneficiaryAccount() {
    }

    public AdmBeneficiaryAccount(@NotNull Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }

    public AdmBeneficiaryAccount(@NotNull Long beneficiaryAccountId, @NotNull LocalDateTime dateCreated) {
        this.beneficiaryAccountId = beneficiaryAccountId;
        this.dateCreated = dateCreated;
    }

    public Long getBeneficiaryAccountId() {
        return beneficiaryAccountId;
    }

    public void setBeneficiaryAccountId(Long beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "AdmBeneficiaryAccount{" +
                "beneficiaryAccountId=" + beneficiaryAccountId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}


