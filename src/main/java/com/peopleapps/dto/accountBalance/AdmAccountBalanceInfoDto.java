package com.peopleapps.dto.accountBalance;

import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"account_balance_id", "account_id", "transaction_no", "annotation",
        "debit", "credit", "balance", "date_created", "transaction_type_id", "transaction_type_description" })
public class AdmAccountBalanceInfoDto {
    private Long account_balance_id;
    private Long account_id;
    private Long transaction_no;
    @JsonbDateFormat(CsnConstants.DATE_FORMAT)
    private LocalDateTime date_created;
    private Double debit;
    private Double credit;
    private Double balance;
    private String annotation;
    private Long transaction_type_id;
    private String transaction_type_description;
    private AdmTypologyDto transactionType;


    public AdmAccountBalanceInfoDto() {
    }

    public AdmAccountBalanceInfoDto(Long account_balance_id, Long account_id, Long transaction_no, LocalDateTime date_created, Double debit, Double credit, Double balance, String annotation, Long transaction_type_id, String transaction_type_description) {
        this.account_balance_id = account_balance_id;
        this.account_id = account_id;
        this.transaction_no = transaction_no;
        this.date_created = date_created;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.annotation = annotation;
        this.transaction_type_id = transaction_type_id;
        this.transaction_type_description = transaction_type_description;
    }

    public AdmAccountBalanceInfoDto(Long account_balance_id, Long account_id, Long transaction_no, LocalDateTime date_created, Double debit, Double credit, Double balance, String annotation, Long transaction_type_id, String transaction_type_description, AdmTypologyDto transactionType) {
        this.account_balance_id = account_balance_id;
        this.account_id = account_id;
        this.transaction_no = transaction_no;
        this.date_created = date_created;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
        this.annotation = annotation;
        this.transaction_type_id = transaction_type_id;
        this.transaction_type_description = transaction_type_description;
        this.transactionType = transactionType;
    }

    public Long getAccount_balance_id() {
        return account_balance_id;
    }

    public void setAccount_balance_id(Long account_balance_id) {
        this.account_balance_id = account_balance_id;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public Long getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(Long transaction_no) {
        this.transaction_no = transaction_no;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Long getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Long transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public String getTransaction_type_description() {
        return transaction_type_description;
    }

    public void setTransaction_type_description(String transaction_type_description) {
        this.transaction_type_description = transaction_type_description;
    }

    public AdmTypologyDto getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(AdmTypologyDto transactionType) {
        this.transactionType = transactionType;
    }
}
