package com.peopleapps.dto.inputs.agency;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(name = "AdmAgencyUserControlDto")
@JsonbPropertyOrder({
        "user", "organization", "isLeader", "status"
})
public class AdmAgencyUserControlDto {

    @NotNull
    private AdmAgencyUserControlCreatedUserDto user;
    @NotNull
    private AdmAgencyUserControlOrganizationDto organization;
    @NotNull
    private Boolean isResponsible;

    private AdmAgencyUserControlTypologyDto status;

    public AdmAgencyUserControlDto() {
    }

    public AdmAgencyUserControlDto(@NotNull AdmAgencyUserControlCreatedUserDto user, @NotNull AdmAgencyUserControlOrganizationDto organization, @NotNull Boolean isResponsible,AdmAgencyUserControlTypologyDto status) {
        this.user = user;
        this.organization = organization;
        this.isResponsible = isResponsible;
        this.status = status;
    }

    public AdmAgencyUserControlCreatedUserDto getUser() {
        return user;
    }

    public void setUser(AdmAgencyUserControlCreatedUserDto user) {
        this.user = user;
    }

    public AdmAgencyUserControlOrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(AdmAgencyUserControlOrganizationDto organization) {
        this.organization = organization;
    }

    public Boolean getIsResponsible() {
        return isResponsible;
    }

    public void setIsResponsible(Boolean isResponsible) {
        this.isResponsible = isResponsible;
    }

    public AdmAgencyUserControlTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmAgencyUserControlTypologyDto status) {
        this.status = status;
    }

    public static class AdmAgencyUserControlCreatedUserDto{
        @NotNull
        private UUID personKey;
        private String personNameComplete;
        private String email;
        private LocalDateTime dateCreated;
        private AdmAgencyUserControlTypologyDto role;
        private AdmAgencyUserControlTypologyDto status;

        public AdmAgencyUserControlCreatedUserDto() {
        }

        public AdmAgencyUserControlCreatedUserDto(@NotNull UUID personKey, String personNameComplete, String email, LocalDateTime dateCreated, AdmAgencyUserControlTypologyDto role, AdmAgencyUserControlTypologyDto status) {
            this.personKey = personKey;
            this.personNameComplete = personNameComplete;
            this.email = email;
            this.dateCreated = dateCreated;
            this.role = role;
            this.status = status;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getPersonNameComplete() {
            return personNameComplete;
        }

        public void setPersonNameComplete(String personNameComplete) {
            this.personNameComplete = personNameComplete;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(LocalDateTime dateCreated) {
            this.dateCreated = dateCreated;
        }

        public AdmAgencyUserControlTypologyDto getRole() {
            return role;
        }

        public void setRole(AdmAgencyUserControlTypologyDto role) {
            this.role = role;
        }

        public AdmAgencyUserControlTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmAgencyUserControlTypologyDto status) {
            this.status = status;
        }
    }

    public static class AdmAgencyUserControlOrganizationDto{
        @NotNull
        private UUID organizationKey;
        private String organizationName;
        private AdmAgencyUserControlTypologyDto status;

        public AdmAgencyUserControlOrganizationDto() {
        }


        public AdmAgencyUserControlOrganizationDto(@NotNull UUID organizationKey, String organizationName, AdmAgencyUserControlTypologyDto status) {
            this.organizationKey = organizationKey;
            this.organizationName = organizationName;
            this.status = status;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public UUID getOrganizationKey() {
            return organizationKey;
        }

        public void setOrganizationKey(UUID organizationKey) {
            this.organizationKey = organizationKey;
        }

        public AdmAgencyUserControlTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmAgencyUserControlTypologyDto status) {
            this.status = status;
        }
    }

    public static class AdmAgencyUserControlTypologyDto{

        private Long typologyId;
        private String description;

        public AdmAgencyUserControlTypologyDto() {
        }

        public AdmAgencyUserControlTypologyDto(Long typologyId, String description) {
            this.typologyId = typologyId;
            this.description = description;
        }

        public Long getTypologyId() {
            return typologyId;
        }

        public void setTypologyId(Long typologyId) {
            this.typologyId = typologyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
