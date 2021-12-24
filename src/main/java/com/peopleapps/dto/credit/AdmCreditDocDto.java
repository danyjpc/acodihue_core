package com.peopleapps.dto.credit;

import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
import java.util.UUID;

public class AdmCreditDocDto {
    public final Long creditId;
    public final String internal_code;
    public final Long calculator_id;
    public final UUID associateKey;
    public final Long person_id;
    public final String name_complete;
    public final Long cui;
    public final String nit;
    public final LocalDate birthday;
    public final String email;
    public final Long no_associate;
    public final Boolean own_house;
    public final String marital_status;
    public final Long phone;
    public final String address;
    public final String statedp;
    public final String city;
    public final Double amount;
    public final Long no_payments;
    public final Double interest_rate;
    public final Double interest_final;
    public final String destiny;
    public final Boolean fiduciary;

    public AdmCreditDocDto(Long creditId, String internal_code, Long calculator_id, UUID associateKey, Long person_id, String name_complete, Long cui, String nit, LocalDate birthday, String email, Long no_associate, Boolean own_house, String marital_status, Long phone, String address, String statedp, String city, Double amount, Long no_payments, Double interest_rate,  Double interest_final, String destiny, Boolean fiduciary) {
        this.creditId = creditId;
        this.calculator_id = calculator_id;
        this.internal_code = internal_code;
        this.associateKey = associateKey;
        this.person_id = person_id;
        this.name_complete = name_complete;
        this.cui = cui;
        this.nit = nit;
        this.birthday = birthday;
        this.email = email;
        this.no_associate = no_associate;
        this.own_house = own_house;
        this.marital_status = marital_status;
        this.phone = phone;
        this.address = address;
        this.statedp = statedp;
        this.city = city;
        this.amount = amount;
        this.no_payments = no_payments;
        this.interest_rate = interest_rate;
        this.interest_final = interest_final;
        this.destiny = destiny;
        this.fiduciary = fiduciary;
    }

    public Long getCreditId() {
        return creditId;
    }

    public String getInternal_code() {
        return internal_code;
    }

    public Long getCalculator_id() {
        return calculator_id;
    }

    public UUID getAssociateKey() {
        return associateKey;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public String getName_complete() {
        return name_complete;
    }

    public Long getCui() {
        return cui;
    }

    public String getNit() {
        return nit;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public Long getNo_associate() {
        return no_associate;
    }

    public Boolean getOwn_house() {
        return own_house;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public Long getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getStatedp() {
        return statedp;
    }

    public String getCity() {
        return city;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getNo_payments() {
        return no_payments;
    }

    public Double getInterest_rate() {
        return interest_rate;
    }

    public Double getInterest_final() {
        return interest_final;
    }

    public String getDestiny() {
        return destiny;
    }

    public Boolean getFiduciary() {
        return fiduciary;
    }
}
