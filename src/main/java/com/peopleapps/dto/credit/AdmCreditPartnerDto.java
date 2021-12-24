package com.peopleapps.dto.credit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmCreditPartnerDto {
    private Long partner_credit_id;
    private UUID person_key;
    private String first_name;
    private String last_name;
    private LocalDateTime date_created;
    private Long status_id;
    private String status_desc;
    private LocalDate person_birthday;
    private String person_mail;
    private String person_name_complete;
    private Long person_phone_id;
    private Long person_phone;
    private Long phone_status_id;
    private String phone_status_desc;
    private Long phone_type_id;
    private String phone_type_desc;

    public AdmCreditPartnerDto(Long partner_credit_id, UUID person_key, String first_name, String last_name, LocalDateTime date_created, Long status_id, String status_desc, LocalDate person_birthday, String person_mail, String person_name_complete, Long person_phone_id, Long person_phone, Long phone_status_id, String phone_status_desc, Long phone_type_id, String phone_type_desc) {
        this.partner_credit_id = partner_credit_id;
        this.person_key = person_key;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_created = date_created;
        this.status_id = status_id;
        this.status_desc = status_desc;
        this.person_birthday = person_birthday;
        this.person_mail = person_mail;
        this.person_name_complete = person_name_complete;
        this.person_phone_id = person_phone_id;
        this.person_phone = person_phone;
        this.phone_status_id = phone_status_id;
        this.phone_status_desc = phone_status_desc;
        this.phone_type_id = phone_type_id;
        this.phone_type_desc = phone_type_desc;
    }

    public Long getPartner_credit_id() {
        return partner_credit_id;
    }

    public UUID getPerson_key() {
        return person_key;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public LocalDate getPerson_birthday() {
        return person_birthday;
    }

    public String getPerson_mail() {
        return person_mail;
    }

    public String getPerson_name_complete() {
        return person_name_complete;
    }

    public Long getPerson_phone_id() {
        return person_phone_id;
    }

    public Long getPerson_phone() {
        return person_phone;
    }

    public Long getPhone_status_id() {
        return phone_status_id;
    }

    public String getPhone_status_desc() {
        return phone_status_desc;
    }

    public Long getPhone_type_id() {
        return phone_type_id;
    }

    public String getPhone_type_desc() {
        return phone_type_desc;
    }
}
