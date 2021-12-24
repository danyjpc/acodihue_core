package com.peopleapps.dto.creditLine;


import com.peopleapps.util.CsnConstants;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonbPropertyOrder({"creditLineId", "organization", "description", "dateCreated", "status", "createdBy"})
public class AdmCreditLineDto {
    private Long creditLineId;
    private AdmCreditLineOrganizationDto organization;
    private String description;
    private AdmCreditLinePersonDto createdBy;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmCreditLineTypologyDto status;

    public AdmCreditLineDto() {
    }

    public AdmCreditLineDto(Long creditLineId) {
        this.creditLineId = creditLineId;
    }

    public AdmCreditLineDto(Long creditLineId, AdmCreditLineOrganizationDto organization, String description, AdmCreditLinePersonDto createdBy, LocalDateTime dateCreated, AdmCreditLineTypologyDto status) {
        this.creditLineId = creditLineId;
        this.organization = organization;
        this.description = description;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.status = status;
    }

    public AdmCreditLineDto(Long creditLineId, AdmCreditLineOrganizationDto organization) {
        this.creditLineId = creditLineId;
        this.organization = organization;
    }

    public Long getCreditLineId() {
        return creditLineId;
    }

    public void setCreditLineId(Long creditLineId) {
        this.creditLineId = creditLineId;
    }

    public AdmCreditLineOrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(AdmCreditLineOrganizationDto organization) {
        this.organization = organization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdmCreditLinePersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmCreditLinePersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmCreditLineTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmCreditLineTypologyDto status) {
        this.status = status;
    }

    public static class AdmCreditLineOrganizationDto {
        private UUID organizationKey;
        private String organizationName;
        private String organizationCommercial;

        public AdmCreditLineOrganizationDto() {
        }

        public AdmCreditLineOrganizationDto(UUID organizationKey, String organizationName, String organizationCommercial) {
            this.organizationKey = organizationKey;
            this.organizationName = organizationName;
            this.organizationCommercial = organizationCommercial;
        }

        public UUID getOrganizationKey() {
            return organizationKey;
        }

        public void setOrganizationKey(UUID organizationKey) {
            this.organizationKey = organizationKey;
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
    }

    public static class AdmCreditLinePersonDto {
        private UUID personKey;
        private String email;

        public AdmCreditLinePersonDto() {
        }

        public AdmCreditLinePersonDto(UUID personKey, String email) {
            this.personKey = personKey;
            this.email = email;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


    public static class AdmCreditLineTypologyDto {
        private Long typologyId;
        private String description;

        public AdmCreditLineTypologyDto() {
        }

        public AdmCreditLineTypologyDto(Long typologyId, String description) {
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
