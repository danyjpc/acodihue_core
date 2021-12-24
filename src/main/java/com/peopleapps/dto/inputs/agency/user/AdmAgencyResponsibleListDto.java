package com.peopleapps.dto.inputs.agency.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "AdmAgencyResponsibleListDto")
//Class used as dto to show responsible users for each agency
public class AdmAgencyResponsibleListDto implements Serializable {

    private UUID organization_key;

    private String organization_name;

    private Long agency_status_id;

    private String agency_status_desc;

    private UUID person_key;

    private String person_name_complete;

    private Boolean organization_responsible_is_responsible;

    private Long organization_responsible_status_id;

    private String organization_responsible_status_desc;

    private LocalDateTime user_date_created;

    private Long user_role_id;

    private String user_role_desc;

    private String person_email;

    private Long user_status_id;

    private String user_status_desc;

    public AdmAgencyResponsibleListDto() {
    }

    public AdmAgencyResponsibleListDto(UUID organization_key, String organization_name, Long agency_status_id, String agency_status_desc, UUID person_key, String person_name_complete, Boolean organization_responsible_is_responsible, Long organization_responsible_status_id, String organization_responsible_status_desc, LocalDateTime user_date_created, Long user_role_id, String user_role_desc, String person_email, Long user_status_id, String user_status_desc) {
        this.organization_key = organization_key;
        this.organization_name = organization_name;
        this.agency_status_id = agency_status_id;
        this.agency_status_desc = agency_status_desc;
        this.person_key = person_key;
        this.person_name_complete = person_name_complete;
        this.organization_responsible_is_responsible = organization_responsible_is_responsible;
        this.organization_responsible_status_id = organization_responsible_status_id;
        this.organization_responsible_status_desc = organization_responsible_status_desc;
        this.user_date_created = user_date_created;
        this.user_role_id = user_role_id;
        this.user_role_desc = user_role_desc;
        this.person_email = person_email;
        this.user_status_id = user_status_id;
        this.user_status_desc = user_status_desc;
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

    public Long getAgency_status_id() {
        return agency_status_id;
    }

    public void setAgency_status_id(Long agency_status_id) {
        this.agency_status_id = agency_status_id;
    }

    public String getAgency_status_desc() {
        return agency_status_desc;
    }

    public void setAgency_status_desc(String agency_status_desc) {
        this.agency_status_desc = agency_status_desc;
    }

    public UUID getPerson_key() {
        return person_key;
    }

    public void setPerson_key(UUID person_key) {
        this.person_key = person_key;
    }

    public String getPerson_name_complete() {
        return person_name_complete;
    }

    public void setPerson_name_complete(String person_name_complete) {
        this.person_name_complete = person_name_complete;
    }

    public Boolean getOrganization_responsible_is_responsible() {
        return organization_responsible_is_responsible;
    }

    public void setOrganization_responsible_is_responsible(Boolean organization_responsible_is_responsible) {
        this.organization_responsible_is_responsible = organization_responsible_is_responsible;
    }

    public Long getOrganization_responsible_status_id() {
        return organization_responsible_status_id;
    }

    public void setOrganization_responsible_status_id(Long organization_responsible_status_id) {
        this.organization_responsible_status_id = organization_responsible_status_id;
    }

    public String getOrganization_responsible_status_desc() {
        return organization_responsible_status_desc;
    }

    public void setOrganization_responsible_status_desc(String organization_responsible_status_desc) {
        this.organization_responsible_status_desc = organization_responsible_status_desc;
    }

    public LocalDateTime getUser_date_created() {
        return user_date_created;
    }

    public void setUser_date_created(LocalDateTime user_date_created) {
        this.user_date_created = user_date_created;
    }

    public Long getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(Long user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getUser_role_desc() {
        return user_role_desc;
    }

    public void setUser_role_desc(String user_role_desc) {
        this.user_role_desc = user_role_desc;
    }

    public String getPerson_email() {
        return person_email;
    }

    public void setPerson_email(String person_email) {
        this.person_email = person_email;
    }

    public Long getUser_status_id() {
        return user_status_id;
    }

    public void setUser_status_id(Long user_status_id) {
        this.user_status_id = user_status_id;
    }

    public String getUser_status_desc() {
        return user_status_desc;
    }

    public void setUser_status_desc(String user_status_desc) {
        this.user_status_desc = user_status_desc;
    }
}
