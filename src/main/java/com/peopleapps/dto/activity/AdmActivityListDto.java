package com.peopleapps.dto.activity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmActivityListDto {

    public final Long activity_id;
    public final Long activity_account_id;
    public final Long destiny_id;
    public final String destiny_desc;
    public final Long activity_economic_id;
    public final String activity_economic_desc;
    public final Boolean is_apparel;
    public final Boolean is_fiduciary;
    public final Double area;
    public final Long unit_measure_id;
    public final String unit_measure_desc;
    public final String unit_measure_value;
    public final Double price;
    public final Double earnings;
    public final Long created_by;
    public final String created_email;
    public final UUID person_key;
    public final LocalDateTime date_created;
    public final Boolean leader;
    public final Long status_id;
    public final String status_desc;
    public final Long quantity;
    public final String annotation;

    public AdmActivityListDto(Long activity_id, Long activity_account_id, Long destiny_id, String destiny_desc, Long activity_economic_id, String activity_economic_desc, Boolean is_apparel, Boolean is_fiduciary, Double area, Long unit_measure_id, String unit_measure_desc, String unit_measure_value, Double price, Double earnings, Long created_by, String created_email, UUID person_key, LocalDateTime date_created, Boolean leader, Long status_id, String status_desc, Long quantity, String annotation) {
        this.activity_id = activity_id;
        this.activity_account_id = activity_account_id;
        this.destiny_id = destiny_id;
        this.destiny_desc = destiny_desc;
        this.activity_economic_id = activity_economic_id;
        this.activity_economic_desc = activity_economic_desc;
        this.is_apparel = is_apparel;
        this.is_fiduciary = is_fiduciary;
        this.area = area;
        this.unit_measure_id = unit_measure_id;
        this.unit_measure_desc = unit_measure_desc;
        this.unit_measure_value = unit_measure_value;
        this.price = price;
        this.earnings = earnings;
        this.created_by = created_by;
        this.created_email = created_email;
        this.person_key = person_key;
        this.date_created = date_created;
        this.leader = leader;
        this.status_id = status_id;
        this.status_desc = status_desc;
        this.quantity = quantity;
        this.annotation = annotation;
    }
}
