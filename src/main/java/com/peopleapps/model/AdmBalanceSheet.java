package com.peopleapps.model;


import com.peopleapps.dto.accountTypeMovementAllowed.AdmAccountTypeMovementListDto;
import com.peopleapps.dto.balanceSheet.AdmBalanceSheetListDto;
import com.peopleapps.dto.beneficiary.AdmBeneficiaryListDto;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "adm_balance_sheet")
@SequenceGenerator(
        name = "admBalanceSheetSequence",
        sequenceName = "adm_balance_sheet_sequence",
        initialValue = 1,
        allocationSize = 1
)
@SqlResultSetMapping(
        name = "admBalanceSheetListDto",
        classes = @ConstructorResult(
                targetClass = AdmBalanceSheetListDto.class,
                columns = {
                        @ColumnResult(name = "adm_balance_sheet_id", type = Long.class),
                        @ColumnResult(name = "account_type_id", type = Long.class),
                        @ColumnResult(name = "account_type_description", type = String.class),
                        @ColumnResult(name = "amount", type = BigDecimal.class)
                }
        )
)
@Schema(name = "AdmBalanceSheet")
@JsonbPropertyOrder({"adm_balance_sheet_id", "account_type", "amount",
        "credit", "created_by", "date_created","status"})
public class AdmBalanceSheet implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admAccountAllowedMovementSequence") //JPA
    @Column(name="adm_balance_sheet_id")
    private Long balanceSheetId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_type")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmTypology accountType;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "credit_id")
    @JoinFetch(value = JoinFetchType.INNER)
    private AdmCredit credit;

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


    public AdmBalanceSheet() {
    }

    public AdmBalanceSheet(@NotNull Long balanceSheetId, @NotNull AdmTypology accountType, @NotNull BigDecimal amount, @NotNull AdmCredit credit, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.balanceSheetId = balanceSheetId;
        this.accountType = accountType;
        this.amount = amount;
        this.credit = credit;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getBalanceSheetId() {
        return balanceSheetId;
    }

    public void setBalanceSheetId(Long balanceSheetId) {
        this.balanceSheetId = balanceSheetId;
    }

    public AdmTypology getAccountType() {
        return accountType;
    }

    public void setAccountType(AdmTypology accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AdmCredit getCredit() {
        return credit;
    }

    public void setCredit(AdmCredit credit) {
        this.credit = credit;
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
        return "AdmBalanceSheet{" +
                "balanceSheetId=" + balanceSheetId +
                ", accountType=" + accountType +
                ", amount=" + amount +
                ", credit=" + credit +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}
