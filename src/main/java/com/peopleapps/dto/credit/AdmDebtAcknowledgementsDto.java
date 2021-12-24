package com.peopleapps.dto.credit;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SqlResultSetMapping(
        name = "admDebtAcknowledgementsDto",
        classes = @ConstructorResult(
                targetClass = AdmDebtAcknowledgementsDto.class,
                columns = {
                        @ColumnResult(name = "address_id", type = Long.class),
                        @ColumnResult(name = "country", type = String.class),
                        @ColumnResult(name = "city", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "address_line", type = String.class),
                        @ColumnResult(name = "extension", type = Long.class),
                        @ColumnResult(name = "no_public", type = String.class),
                        @ColumnResult(name = "credit_id", type = Long.class),
                        @ColumnResult(name = "credit_key", type = UUID.class),
                        @ColumnResult(name = "state_credit", type = LocalDateTime.class),
                        @ColumnResult(name = "date_end_credit", type = LocalDateTime.class),
                        @ColumnResult(name = "no_payments", type = Long.class),
                        @ColumnResult(name = "interest_rate", type = Double.class),
                        @ColumnResult(name = "interest_final", type = Double.class),
                        @ColumnResult(name = "application_date", type = LocalDateTime.class),
                        @ColumnResult(name = "no_period", type = Long.class),
                        @ColumnResult(name = "credit", type = Long.class),
                        @ColumnResult(name = "date_created", type = LocalDateTime.class),
                        @ColumnResult(name = "no_reference", type = Long.class),
                        @ColumnResult(name = "name_farm", type = String.class),
                        @ColumnResult(name = "owner", type = String.class),
                        @ColumnResult(name = "document_type", type = String.class),
                        @ColumnResult(name = "name_complete", type = String.class),
                        @ColumnResult(name = "person_key", type = UUID.class),
                        @ColumnResult(name = "birthday", type = LocalDate.class),
                        @ColumnResult(name = "profession", type = String.class),
                        @ColumnResult(name = "marital_status", type = String.class),
                        @ColumnResult(name = "cui", type = Long.class),
                        @ColumnResult(name = "genre", type = String.class),
                        @ColumnResult(name = "mayan_people", type = String.class),
                        @ColumnResult(name = "partner_name", type = String.class),
                        @ColumnResult(name = "partner_person_key", type = UUID.class),
                        @ColumnResult(name = "partner_birthday", type = LocalDate.class),
                        @ColumnResult(name = "partner_profession", type = String.class),
                        @ColumnResult(name = "partner_marital_status", type = String.class),
                        @ColumnResult(name = "partner_cui", type = Long.class),
                        @ColumnResult(name = "partner_genre", type = String.class),
                        @ColumnResult(name = "partner_mayan_people", type = String.class),
                }
        )
)

public class AdmDebtAcknowledgementsDto {
    // address
    private Long address_id;
    private String country;
    private String city;
    private String state;
    private String address_line;
    private Long extension;
    private String no_public;
    // credit
    private Long credit_id;
    private UUID credit_key;
    private LocalDateTime state_credit;
    private LocalDateTime date_end_credit;
    // Calculator
    private Long no_payments;
    private Double interest_rate;
    private Double interest_final;
    private LocalDateTime application_date;
    private Long no_period;
    private Long credit;
    private LocalDateTime date_created;
    // guarantee
    private Long no_reference;
    private String name_farm;
    private String owner;
    private String document_type;

    // person1
    private String name_complete;
    private UUID person_key;
    private LocalDate birthday;
    private String profession;
    private String marital_status;
    private Long cui;
    private String genre;
    private String mayan_people;
    // partner person
    private String partner_name;
    private UUID partner_person_key;
    private LocalDate partner_birthday;
    private String partner_profession;
    private String partner_marital_status;
    private Long partner_cui;
    private String partner_genre;
    private String partner_mayan_people;
    // general
//    private String nameRepLegal;
//    private String cuiRepLegal;
//    private LocalDate birthdayRepLegal;
//    private String maritalStatusRepLegal;
//    private String professionRepLegal;
//    private String acreditationRepLegal;
//    private String dateAcreditationRepLegal;

    public AdmDebtAcknowledgementsDto() {
    }

    public AdmDebtAcknowledgementsDto(Long address_id, String country, String city, String state, String address_line, Long extension, String no_public, Long credit_id, UUID credit_key, LocalDateTime state_credit, LocalDateTime date_end_credit, Long no_payments, Double interest_rate, Double interest_final, LocalDateTime application_date, Long no_period, Long credit, LocalDateTime date_created, Long no_reference, String name_farm, String owner, String document_type, String name_complete, UUID person_key, LocalDate birthday, String profession, String marital_status, Long cui, String genre, String mayan_people, String partner_name, UUID partner_person_key, LocalDate partner_birthday, String partner_profession, String partner_marital_status, Long partner_cui, String partner_genre, String partner_mayan_people) {
        this.address_id = address_id;
        this.country = country;
        this.city = city;
        this.state = state;
        this.address_line = address_line;
        this.extension = extension;
        this.no_public = no_public;
        this.credit_id = credit_id;
        this.credit_key = credit_key;
        this.state_credit = state_credit;
        this.date_end_credit = date_end_credit;
        this.no_payments = no_payments;
        this.interest_rate = interest_rate;
        this.interest_final = interest_final;
        this.application_date = application_date;
        this.no_period = no_period;
        this.credit = credit;
        this.date_created = date_created;
        this.no_reference = no_reference;
        this.name_farm = name_farm;
        this.owner = owner;
        this.document_type = document_type;
        this.name_complete = name_complete;
        this.person_key = person_key;
        this.birthday = birthday;
        this.profession = profession;
        this.marital_status = marital_status;
        this.cui = cui;
        this.genre = genre;
        this.mayan_people = mayan_people;
        this.partner_name = partner_name;
        this.partner_person_key = partner_person_key;
        this.partner_birthday = partner_birthday;
        this.partner_profession = partner_profession;
        this.partner_marital_status = partner_marital_status;
        this.partner_cui = partner_cui;
        this.partner_genre = partner_genre;
        this.partner_mayan_people = partner_mayan_people;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getAddress_line() {
        return address_line;
    }

    public Long getExtension() {
        return extension;
    }

    public String getNo_public() {
        return no_public;
    }

    public Long getCredit_id() {
        return credit_id;
    }

    public UUID getCredit_key() {
        return credit_key;
    }

    public LocalDateTime getState_credit() {
        return state_credit;
    }

    public LocalDateTime getDate_end_credit() {
        return date_end_credit;
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

    public LocalDateTime getApplication_date() {
        return application_date;
    }

    public Long getNo_period() {
        return no_period;
    }

    public Long getCredit() {
        return credit;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public Long getNo_reference() {
        return no_reference;
    }

    public String getName_farm() {
        return name_farm;
    }

    public String getOwner() {
        return owner;
    }

    public String getDocument_type() {
        return document_type;
    }

    public String getName_complete() {
        return name_complete;
    }

    public UUID getPerson_key() {
        return person_key;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getProfession() {
        return profession;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public Long getCui() {
        return cui;
    }

    public String getGenre() {
        return genre;
    }

    public String getMayan_people() {
        return mayan_people;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public UUID getPartner_person_key() {
        return partner_person_key;
    }

    public LocalDate getPartner_birthday() {
        return partner_birthday;
    }

    public String getPartner_profession() {
        return partner_profession;
    }

    public String getPartner_marital_status() {
        return partner_marital_status;
    }

    public Long getPartner_cui() {
        return partner_cui;
    }

    public String getPartner_genre() {
        return partner_genre;
    }

    public String getPartner_mayan_people() {
        return partner_mayan_people;
    }
//    public String getNameRepLegal() {
//        return nameRepLegal;
//    }
//
//    public void setNameRepLegal(String nameRepLegal) {
//        this.nameRepLegal = nameRepLegal;
//    }
//
//    public String getCuiRepLegal() {
//        return cuiRepLegal;
//    }
//
//    public void setCuiRepLegal(String cuiRepLegal) {
//        this.cuiRepLegal = cuiRepLegal;
//    }
//
//    public LocalDate getBirthdayRepLegal() {
//        return birthdayRepLegal;
//    }
//
//    public void setBirthdayRepLegal(LocalDate birthdayRepLegal) {
//        this.birthdayRepLegal = birthdayRepLegal;
//    }
//
//    public String getMaritalStatusRepLegal() {
//        return maritalStatusRepLegal;
//    }
//
//    public void setMaritalStatusRepLegal(String maritalStatusRepLegal) {
//        this.maritalStatusRepLegal = maritalStatusRepLegal;
//    }
//
//    public String getProfessionRepLegal() {
//        return professionRepLegal;
//    }
//
//    public void setProfessionRepLegal(String professionRepLegal) {
//        this.professionRepLegal = professionRepLegal;
//    }
//
//    public String getAcreditationRepLegal() {
//        return acreditationRepLegal;
//    }
//
//    public void setAcreditationRepLegal(String acreditationRepLegal) {
//        this.acreditationRepLegal = acreditationRepLegal;
//    }
//
//    public String getDateAcreditationRepLegal() {
//        return dateAcreditationRepLegal;
//    }
//
//    public void setDateAcreditationRepLegal(String dateAcreditationRepLegal) {
//        this.dateAcreditationRepLegal = dateAcreditationRepLegal;
//    }
}
