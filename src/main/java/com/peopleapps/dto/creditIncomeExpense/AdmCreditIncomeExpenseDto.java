package com.peopleapps.dto.creditIncomeExpense;

import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.util.List;

@JsonbPropertyOrder({"totalByMonth", "totalByYear", "accounts"})
public class AdmCreditIncomeExpenseDto {

    private List<AdmCreditIncomeExpenseAccount> accounts;
    private BigDecimal totalByYear;
    private BigDecimal totalByMonth;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;

    public AdmCreditIncomeExpenseDto() {
    }

    public AdmCreditIncomeExpenseDto(List<AdmCreditIncomeExpenseAccount> accounts, BigDecimal totalByYear, BigDecimal totalByMonth) {
        this.accounts = accounts;
        this.totalByYear = totalByYear;
        this.totalByMonth = totalByMonth;
    }

    public AdmCreditIncomeExpenseDto(List<AdmCreditIncomeExpenseAccount> accounts, AdmUserInfoDto.AdmPersonUserInfoDto createdBy) {
        this.accounts = accounts;
        this.createdBy = createdBy;
    }

    public List<AdmCreditIncomeExpenseAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AdmCreditIncomeExpenseAccount> accounts) {
        this.accounts = accounts;
    }

    public BigDecimal getTotalByYear() {
        return totalByYear;
    }

    public void setTotalByYear(BigDecimal totalByYear) {
        this.totalByYear = totalByYear;
    }

    public BigDecimal getTotalByMonth() {
        return totalByMonth;
    }

    public void setTotalByMonth(BigDecimal totalByMonth) {
        this.totalByMonth = totalByMonth;
    }

    public AdmUserInfoDto.AdmPersonUserInfoDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUserInfoDto.AdmPersonUserInfoDto createdBy) {
        this.createdBy = createdBy;
    }

    @JsonbPropertyOrder({"creditIncomeExpenseId", "amount", "account"})
    public static class AdmCreditIncomeExpenseAccount {
        private Long creditIncomeExpenseId;
        private BigDecimal amount;
        private AdmTypologyDto account;


        public AdmCreditIncomeExpenseAccount() {
        }

        public AdmCreditIncomeExpenseAccount(Long creditIncomeExpenseId, BigDecimal amount, AdmTypologyDto account) {
            this.creditIncomeExpenseId = creditIncomeExpenseId;
            this.amount = amount;
            this.account = account;
        }

        public Long getCreditIncomeExpenseId() {
            return creditIncomeExpenseId;
        }

        public void setCreditIncomeExpenseId(Long creditIncomeExpenseId) {
            this.creditIncomeExpenseId = creditIncomeExpenseId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public AdmTypologyDto getAccount() {
            return account;
        }

        public void setAccount(AdmTypologyDto account) {
            this.account = account;
        }
    }
}
