package com.peopleapps.dto.accountBalance;


import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAccountBalanceDto {
    private Long account_balance_id;
    private Long account_id;
    private Long transaction_no;
    private Double amount;
    private AdmAccountBalanceTypologyDto transactionType;
    private AdmAccountBalancePersonDto createdBy;
    private LocalDateTime date_created;
    private AdmAccountBalanceTypologyDto status;
    private String annotation;

    public AdmAccountBalanceDto() {
    }

    public AdmAccountBalanceDto(Long account_balance_id, Long account_id, Long transaction_no, Double amount, AdmAccountBalanceTypologyDto transactionType, AdmAccountBalancePersonDto createdBy, LocalDateTime date_created, AdmAccountBalanceTypologyDto status, String annotation) {
        this.account_balance_id = account_balance_id;
        this.account_id = account_id;
        this.transaction_no = transaction_no;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdBy = createdBy;
        this.date_created = date_created;
        this.status = status;
        this.annotation = annotation;
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

    public AdmAccountBalanceTypologyDto getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(AdmAccountBalanceTypologyDto transactionType) {
        this.transactionType = transactionType;
    }

    public AdmAccountBalancePersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmAccountBalancePersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public AdmAccountBalanceTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmAccountBalanceTypologyDto status) {
        this.status = status;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public static class AdmAccountBalanceTypologyDto {
        private Long typologyId;
        private String description;
        private Long parentTypologyId;
        private String parentTypologyDescription;

        public AdmAccountBalanceTypologyDto() {
        }

        public AdmAccountBalanceTypologyDto(Long typologyId, String description, Long parentTypologyId, String parentTypologyDescription) {
            this.typologyId = typologyId;
            this.description = description;
            this.parentTypologyId = parentTypologyId;
            this.parentTypologyDescription = parentTypologyDescription;
        }

        public Long getTypologyId() {
            return typologyId;
        }

        public void setTypologyId(Long typologyId) {
            this.typologyId = typologyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getParentTypologyId() {
            return parentTypologyId;
        }

        public void setParentTypologyId(Long parentTypologyId) {
            this.parentTypologyId = parentTypologyId;
        }

        public String getParentTypologyDescription() {
            return parentTypologyDescription;
        }

        public void setParentTypologyDescription(String parentTypologyDescription) {
            this.parentTypologyDescription = parentTypologyDescription;
        }
    }

    public static class AdmAccountBalancePersonDto {
        private UUID personKey;
        private String firstName;
        private String lastName;
        private String email;
        private String nameComplete;

        public AdmAccountBalancePersonDto() {
        }

        public AdmAccountBalancePersonDto(UUID personKey, String firstName, String lastName, String email, String nameComplete) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.nameComplete = nameComplete;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNameComplete() {
            return nameComplete;
        }

        public void setNameComplete(String nameComplete) {
            this.nameComplete = nameComplete;
        }
    }


}
