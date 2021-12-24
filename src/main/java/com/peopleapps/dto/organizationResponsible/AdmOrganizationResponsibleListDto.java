package com.peopleapps.dto.organizationResponsible;

import java.time.LocalDateTime;

public class AdmOrganizationResponsibleListDto {

    private Long organizationResponsibleId;
    private Long organizationId;
    private String organizationName;
    private String organizationCommercial;
    private Long personId;
    private String personName;
    private String personLastName;
    private LocalDateTime dateCreated;
    private Boolean isResponsible;

    public AdmOrganizationResponsibleListDto(Long organizationResponsibleId, Long organizationId, String organizationName, String organizationCommercial, Long personId, String personName, String personLastName, LocalDateTime dateCreated, Boolean isResponsible) {
        this.organizationResponsibleId = organizationResponsibleId;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationCommercial = organizationCommercial;
        this.personId = personId;
        this.personName = personName;
        this.personLastName = personLastName;
        this.dateCreated = dateCreated;
        this.isResponsible = isResponsible;
    }

    public Long getOrganizationResponsibleId() {
        return organizationResponsibleId;
    }

    public void setOrganizationResponsibleId(Long organizationResponsibleId) {
        this.organizationResponsibleId = organizationResponsibleId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCommercial() {
        return organizationCommercial;
    }

    public void setOrganizationCommercial(String organizationCommercial) {
        this.organizationCommercial = organizationCommercial;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsResponsible() {
        return isResponsible;
    }

    public void setIsResponsible(Boolean responsible) {
        isResponsible = responsible;
    }
}
