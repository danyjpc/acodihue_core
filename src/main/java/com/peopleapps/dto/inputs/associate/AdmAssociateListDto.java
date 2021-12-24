package com.peopleapps.dto.inputs.associate;

import javax.persistence.ColumnResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AdmAssociateListDto {

    private UUID associate_person_key;
    private String associate_name_complete;
    private Long associate_membership_number;
    private LocalDateTime associate_date_created;
    private UUID organization_key;
    private String organization_name;
    private Long associate_cui;
    private Long profession_id;
    private String profession_desc;
    private Double org_entry_contribution;
    private Double organization_entry_fee;
    private String asso_address_line;
    private Long asso_phone;
    //linguistic community
    private Long linguistic_community_id;
    private String linguistic_community_desc;



    public AdmAssociateListDto() {
    }

    public AdmAssociateListDto(UUID associate_person_key, String associate_name_complete, Long associate_membership_number, LocalDateTime associate_date_created, UUID organization_key, String organization_name) {
        this.associate_person_key = associate_person_key;
        this.associate_name_complete = associate_name_complete;
        this.associate_membership_number = associate_membership_number;
        this.associate_date_created = associate_date_created;
        this.organization_key = organization_key;
        this.organization_name = organization_name;
    }

    //constructor for associate preinscription document docx
    public AdmAssociateListDto(UUID associate_person_key, String associate_name_complete, Long associate_membership_number, LocalDateTime associate_date_created, UUID organization_key, String organization_name, Long associate_cui, Long profession_id, String profession_desc, Double org_entry_contribution, Double organization_entry_fee, String asso_address_line, Long asso_phone) {
        this.associate_person_key = associate_person_key;
        this.associate_name_complete = associate_name_complete;
        this.associate_membership_number = associate_membership_number;
        this.associate_date_created = associate_date_created;
        this.organization_key = organization_key;
        this.organization_name = organization_name;
        this.associate_cui = associate_cui;
        this.profession_id = profession_id;
        this.profession_desc = profession_desc;
        this.org_entry_contribution = org_entry_contribution;
        this.organization_entry_fee = organization_entry_fee;
        this.asso_address_line = asso_address_line;
        this.asso_phone = asso_phone;
    }

    //constructor for associate endpoint


    public AdmAssociateListDto(UUID associate_person_key, String associate_name_complete, Long associate_membership_number, LocalDateTime associate_date_created, UUID organization_key, String organization_name, Long associate_cui, Long profession_id, String profession_desc, Double org_entry_contribution, Double organization_entry_fee, String asso_address_line, Long asso_phone, Long linguistic_community_id, String linguistic_community_desc) {
        this.associate_person_key = associate_person_key;
        this.associate_name_complete = associate_name_complete;
        this.associate_membership_number = associate_membership_number;
        this.associate_date_created = associate_date_created;
        this.organization_key = organization_key;
        this.organization_name = organization_name;
        this.associate_cui = associate_cui;
        this.profession_id = profession_id;
        this.profession_desc = profession_desc;
        this.org_entry_contribution = org_entry_contribution;
        this.organization_entry_fee = organization_entry_fee;
        this.asso_address_line = asso_address_line;
        this.asso_phone = asso_phone;
        this.linguistic_community_id = linguistic_community_id;
        this.linguistic_community_desc = linguistic_community_desc;
    }

    public UUID getAssociate_person_key() {
        return associate_person_key;
    }

    public void setAssociate_person_key(UUID associate_person_key) {
        this.associate_person_key = associate_person_key;
    }

    public String getAssociate_name_complete() {
        return associate_name_complete;
    }

    public void setAssociate_name_complete(String associate_name_complete) {
        this.associate_name_complete = associate_name_complete;
    }

    public Long getAssociate_membership_number() {
        return associate_membership_number;
    }

    public void setAssociate_membership_number(Long associate_membership_number) {
        this.associate_membership_number = associate_membership_number;
    }

    public LocalDateTime getAssociate_date_created() {
        return associate_date_created;
    }

    public void setAssociate_date_created(LocalDateTime associate_date_created) {
        this.associate_date_created = associate_date_created;
    }

    public UUID getOrganization_key() {
        return organization_key;
    }

    public void setOrganization_key(UUID organization_key) {
        this.organization_key = organization_key;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public Long getAssociate_cui() {
        return associate_cui;
    }

    public void setAssociate_cui(Long associate_cui) {
        this.associate_cui = associate_cui;
    }


    public Long getProfession_id() {
        return profession_id;
    }

    public void setProfession_id(Long profession_id) {
        this.profession_id = profession_id;
    }

    public String getProfession_desc() {
        return profession_desc;
    }

    public void setProfession_desc(String profession_desc) {
        this.profession_desc = profession_desc;
    }

    public Double getOrg_entry_contribution() {
        return org_entry_contribution;
    }

    public void setOrg_entry_contribution(Double org_entry_contribution) {
        this.org_entry_contribution = org_entry_contribution;
    }

    public Double getOrganization_entry_fee() {
        return organization_entry_fee;
    }

    public void setOrganization_entry_fee(Double organization_entry_fee) {
        this.organization_entry_fee = organization_entry_fee;
    }

    public String getAsso_address_line() {
        return asso_address_line;
    }

    public void setAsso_address_line(String asso_address_line) {
        this.asso_address_line = asso_address_line;
    }

    public Long getAsso_phone() {
        return asso_phone;
    }

    public void setAsso_phone(Long asso_phone) {
        this.asso_phone = asso_phone;
    }

    public Long getLinguistic_community_id() {
        return linguistic_community_id;
    }

    public void setLinguistic_community_id(Long linguistic_community_id) {
        this.linguistic_community_id = linguistic_community_id;
    }

    public String getLinguistic_community_desc() {
        return linguistic_community_desc;
    }

    public void setLinguistic_community_desc(String linguistic_community_desc) {
        this.linguistic_community_desc = linguistic_community_desc;
    }
}
