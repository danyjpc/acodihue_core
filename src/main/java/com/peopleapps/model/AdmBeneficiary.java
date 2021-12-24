package com.peopleapps.model;

import com.peopleapps.dto.beneficiary.AdmBeneficiaryListDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_beneficiary")
@SequenceGenerator(
        name = "admBeneficiarySequence",
        sequenceName = "adm_beneficiary_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admBeneficiaryListDto",
        classes = @ConstructorResult(
                targetClass = AdmBeneficiaryListDto.class,
                columns = {
                        @ColumnResult(name = "beneficiary_id", type = Long.class),
                        @ColumnResult(name = "beneficiary_account_id", type = Long.class),
                        @ColumnResult(name = "person_beneficiary_key", type = UUID.class),
                        @ColumnResult(name = "person_beneficiary_first_name", type = String.class),
                        @ColumnResult(name = "person_beneficiary_last_name", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "kinship_id", type = Long.class),
                        @ColumnResult(name = "kinship_desc", type = String.class),
                        @ColumnResult(name = "percent", type = Double.class),
                        @ColumnResult(name = "age", type = Long.class),
                        @ColumnResult(name = "person_name_complete", type = String.class),
                        @ColumnResult(name = "phone_id", type = Long.class),
                        @ColumnResult(name = "phone", type = Long.class),
                        @ColumnResult(name = "person_birthday", type = LocalDate.class),
                        @ColumnResult(name = "person_email", type = String.class)
                }
        )
)
@Schema(name = "AdmBeneficiary")
@JsonbPropertyOrder({"beneficiaryId", "beneficiaryAccount", "beneficiary", "createdBy",
        "dateCreated", "status", "kinship", "percent", "age"})
public class AdmBeneficiary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admBeneficiarySequence")
    @NotNull
    @Column(name = "beneficiary_id")
    private Long beneficiaryId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_account_id")
    @DefaultValue("0")
    private AdmBeneficiaryAccount beneficiaryAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary")
    @DefaultValue("0")
    private AdmPerson beneficiary;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kinship")
    @NotNull
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology kinship;

    @NotNull
    @Column(name = "percent")
    private Double percent;

    @NotNull
    @Column(name = "age")
    private Long age;

    public AdmBeneficiary() {
    }


    public AdmBeneficiary(@NotNull Long beneficiaryId, @NotNull AdmBeneficiaryAccount beneficiaryAccount, @NotNull AdmPerson beneficiary, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status, @NotNull AdmTypology kinship, @NotNull Double percent, @NotNull Long age) {
        this.beneficiaryId = beneficiaryId;
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiary = beneficiary;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.kinship = kinship;
        this.percent = percent;
        this.age = age;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public AdmBeneficiaryAccount getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(AdmBeneficiaryAccount beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public AdmPerson getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(AdmPerson beneficiary) {
        this.beneficiary = beneficiary;
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

    public AdmTypology getKinship() {
        return kinship;
    }

    public void setKinship(AdmTypology kinship) {
        this.kinship = kinship;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "AdmBeneficiary{" +
                "beneficiaryId=" + beneficiaryId +
                ", beneficiaryAccount=" + beneficiaryAccount +
                ", beneficiary=" + beneficiary +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", kinship=" + kinship +
                ", percent=" + percent +
                ", age=" + age +
                '}';
    }
}

