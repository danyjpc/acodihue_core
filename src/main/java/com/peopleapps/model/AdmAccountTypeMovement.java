package com.peopleapps.model;


import com.peopleapps.dto.account.AdmAccountListDto;
import com.peopleapps.dto.accountTypeMovementAllowed.AdmAccountTypeMovementListDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchAccountListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_account_allowed_movements")
@SequenceGenerator(
        name = "admAccountAllowedMovementSequence",
        sequenceName = "adm_account_allowed_movements_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admAccountTypeMovementListDto",
        classes = @ConstructorResult(
                targetClass = AdmAccountTypeMovementListDto.class,
                columns = {
                        @ColumnResult(name = "adm_account_allowed_movements_id", type = Long.class),
                        @ColumnResult(name = "account_type_id", type = Long.class),
                        @ColumnResult(name = "account_type_desc", type = String.class),

                        @ColumnResult(name = "operation_id", type = Long.class),
                        @ColumnResult(name = "operation_desc", type = String.class),

                        @ColumnResult(name = "operation_type_id", type = Long.class),
                        @ColumnResult(name = "operation_type_desc", type = String.class),

                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),

                        @ColumnResult(name = "date_created", type = LocalDateTime.class),

                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class)
                }
        )
)

@SqlResultSetMapping(
        name = "admAccountTypeMovementListDtoOnlyId",
        classes = @ConstructorResult(
                targetClass = AdmAccountTypeMovementListDto.class,
                columns = {
                        @ColumnResult(name = "adm_account_allowed_movements_id", type = Long.class),
                        @ColumnResult(name = "account_type_id", type = Long.class),
                        @ColumnResult(name = "account_type_desc", type = String.class)
                }
        )
)

@Schema(name = "AdmAccountTypeMovement")
@JsonbPropertyOrder({"adm_account_allowed_movements_id", "account_type", "operation",
        "operation_type", "created_by", "date_created","status"})
public class AdmAccountTypeMovement implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAccountAllowedMovementSequence") //JPA
    @Column(name="adm_account_allowed_movements_id")
    private Long admAccountAllowedMovementsId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology accountType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology operation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology operationType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;


    public AdmAccountTypeMovement() {
    }

    public AdmAccountTypeMovement(@NotNull Long admAccountAllowedMovementsId, @NotNull AdmTypology accountType, @NotNull AdmTypology operation, @NotNull AdmTypology operationType, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
        this.accountType = accountType;
        this.operation = operation;
        this.operationType = operationType;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getAdmAccountAllowedMovementsId() {
        return admAccountAllowedMovementsId;
    }

    public void setAdmAccountAllowedMovementsId(Long admAccountAllowedMovementsId) {
        this.admAccountAllowedMovementsId = admAccountAllowedMovementsId;
    }

    public AdmTypology getAccountType() {
        return accountType;
    }

    public void setAccountType(AdmTypology accountType) {
        this.accountType = accountType;
    }

    public AdmTypology getOperation() {
        return operation;
    }

    public void setOperation(AdmTypology operation) {
        this.operation = operation;
    }

    public AdmTypology getOperationType() {
        return operationType;
    }

    public void setOperationType(AdmTypology operationType) {
        this.operationType = operationType;
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

    @Override
    public String toString() {
        return "AdmAccountTypeMovement{" +
                "admAccountAllowedMovementsId=" + admAccountAllowedMovementsId +
                ", accountType=" + accountType +
                ", operation=" + operation +
                ", operationType=" + operationType +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}
