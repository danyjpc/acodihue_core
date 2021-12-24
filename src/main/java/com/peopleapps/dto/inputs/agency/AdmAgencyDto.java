package com.peopleapps.dto.inputs.agency;

import com.peopleapps.util.UUIDConverter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Converter;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Schema(name = "AdmAgencyDto")
@JsonbPropertyOrder({
        "organizationKey", "organizationName", "entryContribution", "entryFee", "phones", "createdBy"
})
public class AdmAgencyDto implements Serializable {


    private UUID organizationKey;
    @NotNull
    private String organizationName;
    @NotNull
    private Double entryContribution;
    @NotNull
    private Double entryFee;
    @NotNull
    private AdmAgencyPhoneDto agencyPhone;
    @NotNull
    private AdmAgencyAddressDto AgencyAddress;
    @NotNull
    private AdmAgencyCreatedPersonDto createdBy;

    private AdmAgencyTypologyDto status;

    private String internalCode;

    public AdmAgencyDto() {
    }


    public AdmAgencyDto(UUID organizationKey, @NotNull String organizationName, @NotNull Double entryContribution, @NotNull Double entryFee, @NotNull AdmAgencyPhoneDto agencyPhone, @NotNull AdmAgencyAddressDto agencyAddress, @NotNull AdmAgencyCreatedPersonDto createdBy, AdmAgencyTypologyDto status, @NotNull String internalCode) {
        this.organizationKey = organizationKey;
        this.organizationName = organizationName;
        this.entryContribution = entryContribution;
        this.entryFee = entryFee;
        this.agencyPhone = agencyPhone;
        AgencyAddress = agencyAddress;
        this.createdBy = createdBy;
        this.status = status;
        this.internalCode = internalCode;
    }

    public AdmAgencyPhoneDto getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(AdmAgencyPhoneDto agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public AdmAgencyAddressDto getAgencyAddress() {
        return AgencyAddress;
    }

    public void setAgencyAddress(AdmAgencyAddressDto agencyAddress) {
        AgencyAddress = agencyAddress;
    }

    public AdmAgencyCreatedPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmAgencyCreatedPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public AdmAgencyTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmAgencyTypologyDto status) {
        this.status = status;
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

    public Double getEntryContribution() {
        return entryContribution;
    }

    public void setEntryContribution(Double entryContribution) {
        this.entryContribution = entryContribution;
    }

    public Double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(Double entryFee) {
        this.entryFee = entryFee;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public static class AdmAgencyOrganizationDto {

        @NotNull
        private UUID organizationKey;
        @NotNull
        private String organizationName;
        @NotNull
        private Double entryContribution;
        @NotNull
        private Double entryFee;

        public AdmAgencyOrganizationDto() {
        }

        public AdmAgencyOrganizationDto(@NotNull UUID organizationKey, @NotNull String organizationName, @NotNull Double entryContribution, @NotNull Double entryFee) {
            this.organizationKey = organizationKey;
            this.organizationName = organizationName;
            this.entryContribution = entryContribution;
            this.entryFee = entryFee;
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

        public Double getEntryContribution() {
            return entryContribution;
        }

        public void setEntryContribution(Double entryContribution) {
            this.entryContribution = entryContribution;
        }

        public Double getEntryFee() {
            return entryFee;
        }

        public void setEntryFee(Double entryFee) {
            this.entryFee = entryFee;
        }
    }


    @JsonbPropertyOrder({"phoneId", "number"})
    public static class AdmAgencyPhoneDto implements Serializable {


        private Long phoneId;
        @NotNull
        private Long phone;
        @NotNull
        private AdmAgencyTypologyDto type;

        public AdmAgencyPhoneDto() {
        }

        public AdmAgencyPhoneDto(Long phoneId, @NotNull Long phone, @NotNull AdmAgencyTypologyDto type) {
            this.phoneId = phoneId;
            this.phone = phone;
            this.type = type;
        }

        public Long getPhoneId() {
            return phoneId;
        }

        public void setPhoneId(Long phoneId) {
            this.phoneId = phoneId;
        }

        public Long getPhone() {
            return phone;
        }

        public void setPhone(Long phone) {
            this.phone = phone;
        }

        public AdmAgencyTypologyDto getType() {
            return type;
        }

        public void setType(AdmAgencyTypologyDto type) {
            this.type = type;
        }
    }

    @JsonbPropertyOrder({"address", "state", "city", "zone"})
    public static class AdmAgencyAddressDto {

        private Long addressId;
        @NotNull
        private String addressLine;
        @NotNull
        private AdmAgencyTypologyDto state;
        @NotNull
        private AdmAgencyTypologyDto city;
        @NotNull
        private AdmAgencyTypologyDto zone;
        @NotNull
        private AdmAgencyTypologyDto type;

        public AdmAgencyAddressDto() {
        }

        public AdmAgencyAddressDto(Long addressId, @NotNull String addressLine, @NotNull AdmAgencyTypologyDto state, @NotNull AdmAgencyTypologyDto city, @NotNull AdmAgencyTypologyDto zone, @NotNull AdmAgencyTypologyDto type) {
            this.addressId = addressId;
            this.addressLine = addressLine;
            this.state = state;
            this.city = city;
            this.zone = zone;
            this.type = type;
        }

        public Long getAddressId() {
            return addressId;
        }

        public void setAddressId(Long addressId) {
            this.addressId = addressId;
        }

        public String getAddressLine() {
            return addressLine;
        }

        public void setAddressLine(String addressLine) {
            this.addressLine = addressLine;
        }

        public AdmAgencyTypologyDto getState() {
            return state;
        }

        public void setState(AdmAgencyTypologyDto state) {
            this.state = state;
        }

        public AdmAgencyTypologyDto getCity() {
            return city;
        }

        public void setCity(AdmAgencyTypologyDto city) {
            this.city = city;
        }

        public AdmAgencyTypologyDto getZone() {
            return zone;
        }

        public void setZone(AdmAgencyTypologyDto zone) {
            this.zone = zone;
        }

        public AdmAgencyTypologyDto getType() {
            return type;
        }

        public void setType(AdmAgencyTypologyDto type) {
            this.type = type;
        }
    }

    @JsonbPropertyOrder({"typologyId", "description"})
    public static class AdmAgencyTypologyDto implements Serializable {
        @NotNull
        private Long typologyId;

        private String description;

        public AdmAgencyTypologyDto() {
        }

        public AdmAgencyTypologyDto(Long typologyId, String description) {
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

    @JsonbPropertyOrder({"personKey"})
    @Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
    public static class AdmAgencyCreated implements Serializable {

        private AdmAgencyCreatedPersonDto person;

        public AdmAgencyCreated() {
        }

        public AdmAgencyCreated(AdmAgencyCreatedPersonDto person) {
            this.person = person;
        }

        public AdmAgencyCreatedPersonDto getPerson() {
            return person;
        }

        public void setPerson(AdmAgencyCreatedPersonDto person) {
            this.person = person;
        }


    }

    public static class AdmAgencyCreatedPersonDto {
        @NotNull
        private UUID personKey;
        private String firstName;
        private String lastName;

        public AdmAgencyCreatedPersonDto() {
        }

        public AdmAgencyCreatedPersonDto(@NotNull UUID personKey) {
            this.personKey = personKey;
        }

        public AdmAgencyCreatedPersonDto(@NotNull UUID personKey, String firstName, String lastName) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}


