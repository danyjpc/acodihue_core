package com.peopleapps.dto.inputs.associations;


import com.peopleapps.model.AdmOrganization;
import com.peopleapps.util.UUIDConverter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.Converter;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Schema(name = "AdmAssociationsDto")
@JsonbPropertyOrder({"association", "contact", "contactPhone", "user", "status"})
public class AdmAssociationsDto {


    private AdmAssociationOrganizationDto association;

    private AdmAssociationPersonDto contact;

    private AdmAssociationsPhoneDto contactPhone;
    private AdmAssociationsAddressDto contactAddress;

    @NotNull
    private AdmAssociationUserPersonDto createdBy;

    private AdmAssociationsTypologyDto status;

    private String internalCode;

    public AdmAssociationsDto() {
    }

    public AdmAssociationsDto(AdmAssociationOrganizationDto association, AdmAssociationPersonDto contact, AdmAssociationsPhoneDto contactPhone, AdmAssociationsAddressDto contactAddress, @NotNull AdmAssociationUserPersonDto createdBy, AdmAssociationsTypologyDto status, String internalCode) {
        this.association = association;
        this.contact = contact;
        this.contactPhone = contactPhone;
        this.contactAddress = contactAddress;
        this.createdBy = createdBy;
        this.status = status;
        this.internalCode = internalCode;
    }

    public AdmAssociationOrganizationDto getAssociation() {
        return association;
    }

    public void setAssociation(AdmAssociationOrganizationDto association) {
        this.association = association;
    }

    public AdmAssociationPersonDto getContact() {
        return contact;
    }

    public void setContact(AdmAssociationPersonDto contact) {
        this.contact = contact;
    }

    public AdmAssociationsPhoneDto getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(AdmAssociationsPhoneDto contactPhone) {
        this.contactPhone = contactPhone;
    }

    public AdmAssociationsAddressDto getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(AdmAssociationsAddressDto contactAddress) {
        this.contactAddress = contactAddress;
    }


    public AdmAssociationUserPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmAssociationUserPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public AdmAssociationsTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmAssociationsTypologyDto status) {
        this.status = status;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    @JsonbPropertyOrder({"organizationKey", "organizationName"})
    public static class AdmAssociationOrganizationDto {

        private UUID organizationKey;
        @NotNull
        private String organizationName;
        @NotNull
        private Double interestRate;

        public AdmAssociationOrganizationDto() {
        }

        public AdmAssociationOrganizationDto(UUID organizationKey, @NotNull String organizationName, @NotNull Double interestRate) {
            this.organizationKey = organizationKey;
            this.organizationName = organizationName;
            this.interestRate = interestRate;
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

        public Double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(Double interestRate) {
            this.interestRate = interestRate;
        }
    }

    @JsonbPropertyOrder("nameComplete")
    public static class AdmAssociationPersonDto {

        private UUID personKey;
        private String nameComplete;

        public AdmAssociationPersonDto() {
        }

        public AdmAssociationPersonDto(UUID personKey, String nameComplete) {
            this.personKey = personKey;
            this.nameComplete = nameComplete;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }

        public String getNameComplete() {
            return nameComplete;
        }

        public void setNameComplete(String nameComplete) {
            this.nameComplete = nameComplete;
        }
    }

    @JsonbPropertyOrder({"phoneId", "number", "type"})
    public static class AdmAssociationsPhoneDto {

        private Long phoneId;
        @NotNull
        private Long phone;
        @NotNull
        private AdmAssociationsTypologyDto type;

        public AdmAssociationsPhoneDto() {
        }

        public AdmAssociationsPhoneDto(Long phoneId, @NotNull Long phone, @NotNull AdmAssociationsTypologyDto type) {
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

        public AdmAssociationsTypologyDto getType() {
            return type;
        }

        public void setType(AdmAssociationsTypologyDto type) {
            this.type = type;
        }
    }

    @JsonbPropertyOrder({"addressId", "addressLine", "state", "city", "zone", "type"})
    public static class AdmAssociationsAddressDto {

        private Long addressId;
        @NotNull
        private String addressLine;
        @NotNull
        private AdmAssociationsTypologyDto state;
        @NotNull
        private AdmAssociationsTypologyDto city;
        @NotNull
        private AdmAssociationsTypologyDto zone;
        @NotNull
        private AdmAssociationsTypologyDto type;

        public AdmAssociationsAddressDto() {
        }

        public AdmAssociationsAddressDto(Long addressId, @NotNull String addressLine, @NotNull AdmAssociationsTypologyDto state, @NotNull AdmAssociationsTypologyDto city, @NotNull AdmAssociationsTypologyDto zone, @NotNull AdmAssociationsTypologyDto type) {
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

        public AdmAssociationsTypologyDto getState() {
            return state;
        }

        public void setState(AdmAssociationsTypologyDto state) {
            this.state = state;
        }

        public AdmAssociationsTypologyDto getCity() {
            return city;
        }

        public void setCity(AdmAssociationsTypologyDto city) {
            this.city = city;
        }

        public AdmAssociationsTypologyDto getZone() {
            return zone;
        }

        public void setZone(AdmAssociationsTypologyDto zone) {
            this.zone = zone;
        }

        public AdmAssociationsTypologyDto getType() {
            return type;
        }

        public void setType(AdmAssociationsTypologyDto type) {
            this.type = type;
        }
    }

    public static class AdmAssociationsTypologyDto {
        private Long typologyId;
        private String description;

        public AdmAssociationsTypologyDto() {
        }

        public AdmAssociationsTypologyDto(Long typologyId, String description) {
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


    @JsonbPropertyOrder({"personKey", "firstName", "lastName"})
    @Converter(name = "uuidConverter", converterClass = UUIDConverter.class)
    public static class AdmAssociationUser implements Serializable {

        private AdmAssociationUserPersonDto person;

        public AdmAssociationUser() {
        }

        public AdmAssociationUser(AdmAssociationUserPersonDto person) {
            this.person = person;
        }

        public AdmAssociationUserPersonDto getPerson() {
            return person;
        }

        public void setPerson(AdmAssociationUserPersonDto person) {
            this.person = person;
        }
    }

    public static class AdmAssociationUserPersonDto {

        @NotNull
        private UUID personKey;
        private String firstName;
        private String lastName;

        public AdmAssociationUserPersonDto() {
        }

        public AdmAssociationUserPersonDto(@NotNull UUID personKey) {
            this.personKey = personKey;
        }

        public AdmAssociationUserPersonDto(@NotNull UUID personKey, String firstName, String lastName) {
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
