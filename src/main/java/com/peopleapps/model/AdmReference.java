package com.peopleapps.model;

import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.dto.reference.AdmReferenceListDto;
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
@Table(name = "adm_reference")
@SequenceGenerator(
        name = "admReferenceSequence",
        sequenceName = "adm_reference_sequence",
        initialValue = 1,
        allocationSize = 1

)
@SqlResultSetMapping(
        name = "admReferenceListDto",
        classes = @ConstructorResult(
                targetClass = AdmReferenceListDto.class,
                columns = {
                        @ColumnResult(name = "reference_id", type = Long.class),
                        @ColumnResult(name = "reference_account_id", type = Long.class),
                        @ColumnResult(name = "reference_person_key", type = UUID.class),
                        @ColumnResult(name = "reference_first_name", type = String.class),
                        @ColumnResult(name = "reference_last_name", type = String.class),
                        @ColumnResult(name = "reference_name_complete", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_typology_id", type = Long.class),
                        @ColumnResult(name = "status_description", type = String.class),
                        @ColumnResult(name = "kinship_typology_id", type = Long.class),
                        @ColumnResult(name = "kinship_description", type = String.class),
                        @ColumnResult(name = "age", type = Long.class),
                        @ColumnResult(name = "phone_account_id", type = Long.class),
                        @ColumnResult(name = "phone_id", type = Long.class),
                        @ColumnResult(name = "phone", type = Long.class),
                        @ColumnResult(name = "phone_type_typology_id", type = Long.class),
                        @ColumnResult(name = "phone_type_description", type = String.class),
                        @ColumnResult(name = "person_email", type = String.class),
                        @ColumnResult(name = "person_birthday", type = LocalDate.class)
                }
        )
)
@Schema(name = "AdmReference")
@JsonbPropertyOrder({"referenceId","referenceAccount","reference",
        "createdBy","dateCreated","status","kinship","age"})
public class AdmReference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admReferenceSequence")
    @NotNull
    @Column(name = "reference_id")
    private Long referenceId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_account_id")
    @DefaultValue("0")
    private AdmReferenceAccount referenceAccount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference")
    @DefaultValue("0")
    private AdmPerson reference;

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
    @Column(name = "age")
    private Long age;

    public AdmReference() {
    }

    public AdmReference(@NotNull Long referenceId, @NotNull AdmReferenceAccount referenceAccount, @NotNull AdmPerson reference, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status, @NotNull AdmTypology kinship, @NotNull Long age) {
        this.referenceId = referenceId;
        this.referenceAccount = referenceAccount;
        this.reference = reference;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
        this.kinship = kinship;
        this.age = age;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public AdmReferenceAccount getReferenceAccount() {
        return referenceAccount;
    }

    public void setReferenceAccount(AdmReferenceAccount referenceAccount) {
        this.referenceAccount = referenceAccount;
    }

    public AdmPerson getReference() {
        return reference;
    }

    public void setReference(AdmPerson reference) {
        this.reference = reference;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
