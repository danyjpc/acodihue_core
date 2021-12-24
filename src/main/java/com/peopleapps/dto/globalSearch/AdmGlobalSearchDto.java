package com.peopleapps.dto.globalSearch;

import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.person.AdmPersonDto;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.List;

@JsonbPropertyOrder({
        "person"
})
public class AdmGlobalSearchDto {
    private String global_search_type;
    private List<AdmGlobalSearchRecord> records;

    public AdmGlobalSearchDto() {
    }

    public AdmGlobalSearchDto(String global_search_type, List<AdmGlobalSearchRecord> records) {
        this.global_search_type = global_search_type;
        this.records = records;
    }

    public String getGlobal_search_type() {
        return global_search_type;
    }

    public void setGlobal_search_type(String global_search_type) {
        this.global_search_type = global_search_type;
    }

    public List<AdmGlobalSearchRecord> getRecords() {
        return records;
    }

    public void setRecords(List<AdmGlobalSearchRecord> records) {
        this.records = records;
    }

    public static class AdmGlobalSearchRecord {
        AdmPersonDto person;
        AdmAccountDto account;
        AdmCreditDto credit;

        public AdmGlobalSearchRecord() {
        }

        public AdmGlobalSearchRecord(AdmAccountDto account) {
            this.account = account;
        }

        public AdmGlobalSearchRecord(AdmPersonDto person) {
            this.person = person;
        }

        public AdmGlobalSearchRecord(AdmCreditDto credit) {
            this.credit = credit;
        }

        public AdmPersonDto getPerson() {
            return person;
        }

        public void setPerson(AdmPersonDto person) {
            this.person = person;
        }

        public AdmAccountDto getAccount() {
            return account;
        }

        public void setAccount(AdmAccountDto account) {
            this.account = account;
        }

        public AdmCreditDto getCredit() {
            return credit;
        }

        public void setCredit(AdmCreditDto credit) {
            this.credit = credit;
        }
    }
}
