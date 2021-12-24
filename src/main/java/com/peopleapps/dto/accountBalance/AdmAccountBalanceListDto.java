package com.peopleapps.dto.accountBalance;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAccountBalanceListDto {
    private Long account_balance_id;
    private Long account_id;
    private Long transaction_no;
    private Double amount;
    private Long transaction_type_id;
    private String transaction_type_desc;
    private String created_by_email;
    private UUID created_by_person_key;
    private LocalDateTime date_created;
    private Long status_id;
    private String status_desc;
    private String annotation;
    private Long transaction_type_parent_id;
    private String transaction_type_parent_desc;

    public AdmAccountBalanceListDto(Long account_balance_id, Long account_id, Long transaction_no, Double amount, Long transaction_type_id, String transaction_type_desc, String created_by_email, UUID created_by_person_key, LocalDateTime date_created, Long status_id, String status_desc, String annotation, Long transaction_type_parent_id, String transaction_type_parent_desc) {
        this.account_balance_id = account_balance_id;
        this.account_id = account_id;
        this.transaction_no = transaction_no;
        this.amount = amount;
        this.transaction_type_id = transaction_type_id;
        this.transaction_type_desc = transaction_type_desc;
        this.created_by_email = created_by_email;
        this.created_by_person_key = created_by_person_key;
        this.date_created = date_created;
        this.status_id = status_id;
        this.status_desc = status_desc;
        this.annotation = annotation;
        this.transaction_type_parent_id = transaction_type_parent_id;
        this.transaction_type_parent_desc = transaction_type_parent_desc;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(Long transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public String getTransaction_type_desc() {
        return transaction_type_desc;
    }

    public void setTransaction_type_desc(String transaction_type_desc) {
        this.transaction_type_desc = transaction_type_desc;
    }

    public String getCreated_by_email() {
        return created_by_email;
    }

    public void setCreated_by_email(String created_by_email) {
        this.created_by_email = created_by_email;
    }

    public UUID getCreated_by_person_key() {
        return created_by_person_key;
    }

    public void setCreated_by_person_key(UUID created_by_person_key) {
        this.created_by_person_key = created_by_person_key;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Long getTransaction_type_parent_id() {
        return transaction_type_parent_id;
    }

    public void setTransaction_type_parent_id(Long transaction_type_parent_id) {
        this.transaction_type_parent_id = transaction_type_parent_id;
    }

    public String getTransaction_type_parent_desc() {
        return transaction_type_parent_desc;
    }

    public void setTransaction_type_parent_desc(String transaction_type_parent_desc) {
        this.transaction_type_parent_desc = transaction_type_parent_desc;
    }
}
