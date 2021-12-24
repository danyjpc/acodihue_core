package com.peopleapps.model;


import com.peopleapps.dto.balanceSheet.AdmBalanceSheetListDto;
import com.peopleapps.dto.creditIncomeExpense.AdmCreditIncomeExpenseListDto;
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
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_credit_income_expense")
@SequenceGenerator(
        name = "admCreditIncomeExpenseSequence",
        sequenceName = "adm_credit_income_expense_sequence",
        initialValue = 1,
        allocationSize = 1
)
@SqlResultSetMapping(
        name = "admCreditIncomeExpenseListDto",
        classes = @ConstructorResult(
                targetClass = AdmCreditIncomeExpenseListDto.class,
                columns = {
                        @ColumnResult(name = "credit_income_expense_id", type = Long.class),
                        @ColumnResult(name = "account_type_id", type = Long.class),
                        @ColumnResult(name = "account_type_description", type = String.class),
                        @ColumnResult(name = "amount", type = BigDecimal.class)
                }
        )
)
@Schema(name = "AdmCreditIncomeExpense")
@JsonbPropertyOrder({"creditIncomeExpenseId", "accountType", "amount",
        "credit", "createdBy", "dateCreated", "status"})
public class AdmCreditIncomeExpense implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admCreditIncomeExpenseSequence") //JPA
    @Column(name = "credit_income_expense_id")
    private Long creditIncomeExpenseId;

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


    public AdmCreditIncomeExpense() {
    }

    public AdmCreditIncomeExpense(@NotNull Long creditIncomeExpenseId, @NotNull AdmTypology accountType, @NotNull BigDecimal amount, @NotNull AdmCredit credit, @NotNull AdmUser createdBy, @NotNull LocalDateTime dateCreated, @NotNull AdmTypology status) {
        this.creditIncomeExpenseId = creditIncomeExpenseId;
        this.accountType = accountType;
        this.amount = amount;
        this.credit = credit;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public Long getCreditIncomeExpenseId() {
        return creditIncomeExpenseId;
    }

    public void setCreditIncomeExpenseId(Long creditIncomeExpenseId) {
        this.creditIncomeExpenseId = creditIncomeExpenseId;
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
        return "AdmCreditIncomeExpense{" +
                "creditIncomeExpenseId=" + creditIncomeExpenseId +
                ", accountType=" + accountType +
                ", amount=" + amount +
                ", credit=" + credit +
                ", createdBy=" + createdBy +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}
