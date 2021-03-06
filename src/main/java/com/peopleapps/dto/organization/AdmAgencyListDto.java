package com.peopleapps.dto.organization;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAgencyListDto {
    public final Long organization_id;
    public final String organization_name;
    public final String organization_commercial;
    public final Long address_account_id;
    public final Long phone_account_id;
    public final Long document_account_id;
    public final Long sector_id;
    public final String sector_description;
    public final Long category_id;
    public final String category_description;
    public final LocalDateTime date_created;
    public final UUID organization_key;
    public final String tax_code;
    public final Boolean is_agency;
    public final Boolean is_society;
    public final Boolean is_organization;
    public final Double entry_contribution;
    public final Double entry_fee;
    public final Long status_id;
    public final String status_desc;

    public final Long parent_organization_id;
    public final String parent_organization_name;
    public final UUID parent_organization_key;

    public final Long created_user_id;
    public final Long created_person_id;
    public final String created_person_first_name;
    public final String created_person_last_name;
    public final UUID created_person_key;
    public final Long phone_id;
    public final Long phone;
    public final Long phone_agency_type_id;
    public final String phone_agency_type_desc;
    public final Long address_id;
    public final String address;
    public final Long address_agency_type_id;
    public final String address_agency_type_desc;
    public final Long address_agency_country_id;
    public final String address_agency_country_desc;
    public final Long address_agency_state_id;
    public final String address_agency_state_desc;
    public final Long address_agency_city_id;
    public final String address_agency_city_desc;
    public final Long address_agency_zone_id;
    public final String address_agency_zone_desc;
    public final String internal_code;

    public AdmAgencyListDto(Long organization_id, String organization_name, String organization_commercial, Long address_account_id, Long phone_account_id, Long document_account_id, Long sector_id, String sector_description, Long category_id, String category_description, LocalDateTime date_created, UUID organization_key, String tax_code, Boolean is_agency, Boolean is_society, Boolean is_organization, Double entry_contribution, Double entry_fee, Long status_id, String status_desc, Long parent_organization_id, String parent_organization_name, UUID parent_organization_key, Long created_user_id, Long created_person_id, String created_person_first_name, String created_person_last_name, UUID created_person_key, Long phone_id, Long phone, Long phone_agency_type_id, String phone_agency_type_desc, Long address_id, String address, Long address_agency_type_id, String address_agency_type_desc, Long address_agency_country_id, String address_agency_country_desc, Long address_agency_state_id, String address_agency_state_desc, Long address_agency_city_id, String address_agency_city_desc, Long address_agency_zone_id, String address_agency_zone_desc, String internal_code) {
        this.organization_id = organization_id;
        this.organization_name = organization_name;
        this.organization_commercial = organization_commercial;
        this.address_account_id = address_account_id;
        this.phone_account_id = phone_account_id;
        this.document_account_id = document_account_id;
        this.sector_id = sector_id;
        this.sector_description = sector_description;
        this.category_id = category_id;
        this.category_description = category_description;
        this.date_created = date_created;
        this.organization_key = organization_key;
        this.tax_code = tax_code;
        this.is_agency = is_agency;
        this.is_society = is_society;
        this.is_organization = is_organization;
        this.entry_contribution = entry_contribution;
        this.entry_fee = entry_fee;
        this.status_id = status_id;
        this.status_desc = status_desc;
        this.parent_organization_id = parent_organization_id;
        this.parent_organization_name = parent_organization_name;
        this.parent_organization_key = parent_organization_key;
        this.created_user_id = created_user_id;
        this.created_person_id = created_person_id;
        this.created_person_first_name = created_person_first_name;
        this.created_person_last_name = created_person_last_name;
        this.created_person_key = created_person_key;
        this.phone_id = phone_id;
        this.phone = phone;
        this.phone_agency_type_id = phone_agency_type_id;
        this.phone_agency_type_desc = phone_agency_type_desc;
        this.address_id = address_id;
        this.address = address;
        this.address_agency_type_id = address_agency_type_id;
        this.address_agency_type_desc = address_agency_type_desc;
        this.address_agency_country_id = address_agency_country_id;
        this.address_agency_country_desc = address_agency_country_desc;
        this.address_agency_state_id = address_agency_state_id;
        this.address_agency_state_desc = address_agency_state_desc;
        this.address_agency_city_id = address_agency_city_id;
        this.address_agency_city_desc = address_agency_city_desc;
        this.address_agency_zone_id = address_agency_zone_id;
        this.address_agency_zone_desc = address_agency_zone_desc;
        this.internal_code = internal_code;
    }
}
