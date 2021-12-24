package com.peopleapps.dto.balanceSheet;

import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonbPropertyOrder({"total", "accounts"})
public class AdmBalanceSheetDto {

    private List<AdmBalanceSheetAccount> accounts;
    private BigDecimal total;
    private AdmUserInfoDto.AdmPersonUserInfoDto createdBy;

    public AdmBalanceSheetDto() {
    }

    public AdmBalanceSheetDto(List<AdmBalanceSheetAccount> accounts, BigDecimal total) {
        this.accounts = accounts;
        this.total = total;
    }

    public AdmBalanceSheetDto(List<AdmBalanceSheetAccount> accounts, AdmUserInfoDto.AdmPersonUserInfoDto createdBy) {
        this.accounts = accounts;
        this.createdBy = createdBy;
    }

    public List<AdmBalanceSheetAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AdmBalanceSheetAccount> accounts) {
        this.accounts = accounts;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public AdmUserInfoDto.AdmPersonUserInfoDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmUserInfoDto.AdmPersonUserInfoDto createdBy) {
        this.createdBy = createdBy;
    }

    @JsonbPropertyOrder({"balanceSheetId", "amount", "account"})
    public static class AdmBalanceSheetAccount {
        private Long balanceSheetId;
        private BigDecimal amount;
        private AdmTypologyDto account;


        public AdmBalanceSheetAccount() {
        }

        public AdmBalanceSheetAccount(Long balanceSheetId, BigDecimal amount, AdmTypologyDto account) {
            this.balanceSheetId = balanceSheetId;
            this.amount = amount;
            this.account = account;
        }


        public Long getBalanceSheetId() {
            return balanceSheetId;
        }

        public void setBalanceSheetId(Long balanceSheetId) {
            this.balanceSheetId = balanceSheetId;
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
