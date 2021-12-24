package com.peopleapps.model;


import com.peopleapps.dto.account.AdmAccountListDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceListDto;
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
@Table(name = "adm_account_balance")
@SequenceGenerator(
        name = "admAccountBalanceSequence",
        sequenceName = "adm_account_balance_sequence",
        initialValue = 1,
        allocationSize = 1
)

@SqlResultSetMapping(
        name = "admAccountBalanceListDto",
        classes = @ConstructorResult(
                targetClass = AdmAccountBalanceListDto.class,
                columns = {
                        @ColumnResult(name = "account_balance_id", type = Long.class),
                        @ColumnResult(name = "account_id", type = Long.class),
                        @ColumnResult(name = "transaction_no", type = Long.class),
                        @ColumnResult(name = "amount", type = Double.class),
                        @ColumnResult(name = "transaction_type_id", type = Long.class),
                        @ColumnResult(name = "transaction_type_desc", type = String.class),
                        @ColumnResult(name = "created_by_email", type = String.class),
                        @ColumnResult(name = "created_by_person_key", type = UUID.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "status_id", type = Long.class),
                        @ColumnResult(name = "status_desc", type = String.class),
                        @ColumnResult(name = "annotation", type = String.class),
                        @ColumnResult(name = "transaction_type_parent_id", type = Long.class),
                        @ColumnResult(name = "transaction_type_parent_desc", type = String.class),
                }
        )
)


@Schema(name = "AdmAccountBalance")
@JsonbPropertyOrder({"accountId", "organizationResponsibleId", "accountType", "numAccount",
        "numAccountOrder", "createdBy", "dateCreated", "status"})
public class AdmAccountBalance implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAccountBalanceSequence") //JPA
    @Column(name = "account_balance_id")
    private Long accountBalanceId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmAccount account;

    @NotNull
    @Column(name = "transaction_no")
    private Long transactionNo;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology transactionType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmUser createdBy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology status;

    @NotNull
    @Column(name = "date_created")
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;

    @NotNull
    @Column(name = "annotation")
    private String annotation;



    public AdmAccountBalance() {
    }

    public AdmAccountBalance(@NotNull Long accountBalanceId, @NotNull AdmAccount account, @NotNull Long transactionNo, @NotNull Double amount, @NotNull AdmTypology transactionType, @NotNull AdmUser createdBy, @NotNull AdmTypology status, @NotNull LocalDateTime dateCreated, @NotNull String annotation) {
        this.accountBalanceId = accountBalanceId;
        this.account = account;
        this.transactionNo = transactionNo;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdBy = createdBy;
        this.status = status;
        this.dateCreated = dateCreated;
        this.annotation = annotation;
    }

    public Long getAccountBalanceId() {
        return accountBalanceId;
    }

    public void setAccountBalanceId(Long accountBalanceId) {
        this.accountBalanceId = accountBalanceId;
    }

    public AdmAccount getAccount() {
        return account;
    }

    public void setAccount(AdmAccount account) {
        this.account = account;
    }

    public Long getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Long transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public AdmTypology getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(AdmTypology transactionType) {
        this.transactionType = transactionType;
    }

    public AdmUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUser createdBy) {
        this.createdBy = createdBy;
    }

    public AdmTypology getStatus() {
        return status;
    }

    public void setStatus(AdmTypology status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "AdmAccountBalance{" +
                "accountBalanceId=" + accountBalanceId +
                ", account=" + account +
                ", transactionNo=" + transactionNo +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", createdBy=" + createdBy +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
