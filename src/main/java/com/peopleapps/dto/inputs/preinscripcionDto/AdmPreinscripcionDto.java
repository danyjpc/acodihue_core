package com.peopleapps.dto.inputs.preinscripcionDto;

import com.peopleapps.dto.inputs.user.AdmUserRegistryDto;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(name = "AdmPreinscripcionDto")
@JsonbPropertyOrder({"associate",
        "partner",
        "beneficiary",
        "createdBy"})
public class AdmPreinscripcionDto implements Serializable {
    private AdmPreinscripcionPersonDto associate;
    private AdmPreinscripcionPersonDto partner;
    private AdmPreinscripcionBeneficiaryDto beneficiary;
    private AdmPreinscripcionPersonDto createdBy;
    private AdmPreinscripcionOrganizationDto organization;
    private Double extraContribution;


    public AdmPreinscripcionDto() {

    }

    public AdmPreinscripcionDto(AdmPreinscripcionPersonDto associate, AdmPreinscripcionPersonDto partner, AdmPreinscripcionBeneficiaryDto beneficiary, AdmPreinscripcionPersonDto createdBy, AdmPreinscripcionOrganizationDto organization, Double extraContribution) {
        this.associate = associate;
        this.partner = partner;
        this.beneficiary = beneficiary;
        this.createdBy = createdBy;
        this.organization = organization;
        this.extraContribution = extraContribution;
    }

    public AdmPreinscripcionPersonDto getAssociate() {
        return associate;
    }

    public void setAssociate(AdmPreinscripcionPersonDto associate) {
        this.associate = associate;
    }

    public AdmPreinscripcionPersonDto getPartner() {
        return partner;
    }

    public void setPartner(AdmPreinscripcionPersonDto partner) {
        this.partner = partner;
    }

    public AdmPreinscripcionBeneficiaryDto getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(AdmPreinscripcionBeneficiaryDto beneficiary) {
        this.beneficiary = beneficiary;
    }

    public AdmPreinscripcionPersonDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmPreinscripcionPersonDto createdBy) {
        this.createdBy = createdBy;
    }

    public AdmPreinscripcionOrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(AdmPreinscripcionOrganizationDto organization) {
        this.organization = organization;
    }

    public Double getExtraContribution() {
        return extraContribution;
    }

    public void setExtraContribution(Double extraContribution) {
        this.extraContribution = extraContribution;
    }

    //Person fields
    @Schema(name = "PersonPreinscripcionDto")
    @JsonbPropertyOrder({"firstName", "middleName", "personLastName", "lastName",
            "partnerName", "marriedName", "cui", "birthday",
            "age", "genre", "maritalStatus", "mayanPeople",
            "profession", "email", "address", "phones"})
    public static class AdmPreinscripcionPersonDto implements Serializable {
        private UUID personKey;
        private String firstName;
        private String middleName;
        private String lastName;
        private String partnerName;
        private String marriedName;
        private Long cui;
        @JsonbDateFormat(value = CsnConstants.ONLY_DATE_FORMAT)
        private LocalDate birthday;
        private AdmPreinscripcionTypologyDto genre;
        private AdmPreinscripcionTypologyDto maritalStatus;
        private AdmPreinscripcionTypologyDto mayanPeople;
        private AdmPreinscripcionTypologyDto profession;
        private String email;
        private AdmPreinscripcionAddressDto address;
        private List<AdmPreinscripcionPhoneDto> phones;
        private String nameComplete;
        private AdmPreinscripcionTypologyDto status;
        private String membershipNumber;
        @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
        private LocalDateTime dateCreated;
        private AdmPreinscripcionTypologyDto linguisticCommunity;
        //new property nit
        private String nit;

        public AdmPreinscripcionPersonDto() {
        }

        public AdmPreinscripcionPersonDto(String nameComplete) {
            this.nameComplete = nameComplete;
        }

        public AdmPreinscripcionPersonDto(UUID personKey, String nameComplete, String membershipNumber, LocalDateTime dateCreated) {
            this.personKey = personKey;
            this.nameComplete = nameComplete;
            this.membershipNumber = membershipNumber;
            this.dateCreated = dateCreated;

        }

        public AdmPreinscripcionPersonDto(UUID personKey, Long cui, AdmPreinscripcionTypologyDto profession, AdmPreinscripcionAddressDto address, List<AdmPreinscripcionPhoneDto> phones, String nameComplete, String membershipNumber, LocalDateTime dateCreated,AdmPreinscripcionTypologyDto linguisticCommunity) {
            this.personKey = personKey;
            this.cui = cui;
            this.profession = profession;
            this.address = address;
            this.phones = phones;
            this.nameComplete = nameComplete;
            this.membershipNumber = membershipNumber;
            this.dateCreated = dateCreated;
            this.linguisticCommunity = linguisticCommunity;
        }

        public AdmPreinscripcionPersonDto(UUID personKey, String firstName, String middleName, String lastName, String partnerName, String marriedName, Long cui, LocalDate birthday, AdmPreinscripcionTypologyDto genre, AdmPreinscripcionTypologyDto maritalStatus, AdmPreinscripcionTypologyDto mayanPeople, AdmPreinscripcionTypologyDto profession, String email, AdmPreinscripcionAddressDto address, List<AdmPreinscripcionPhoneDto> phones, String nameComplete, AdmPreinscripcionTypologyDto status, String membershipNumber, LocalDateTime dateCreated, AdmPreinscripcionTypologyDto linguisticCommunity, String nit) {
            this.personKey = personKey;
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.partnerName = partnerName;
            this.marriedName = marriedName;
            this.cui = cui;
            this.birthday = birthday;
            this.genre = genre;
            this.maritalStatus = maritalStatus;
            this.mayanPeople = mayanPeople;
            this.profession = profession;
            this.email = email;
            this.address = address;
            this.phones = phones;
            this.nameComplete = nameComplete;
            this.status = status;
            this.membershipNumber = membershipNumber;
            this.dateCreated = dateCreated;
            this.linguisticCommunity = linguisticCommunity;
            this.nit = nit;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPartnerName() {
            return partnerName;
        }

        public void setPartnerName(String partnerName) {
            this.partnerName = partnerName;
        }

        public String getMarriedName() {
            return marriedName;
        }

        public void setMarriedName(String marriedName) {
            this.marriedName = marriedName;
        }

        public Long getCui() {
            return cui;
        }

        public void setCui(Long cui) {
            this.cui = cui;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        public AdmPreinscripcionTypologyDto getGenre() {
            return genre;
        }

        public void setGenre(AdmPreinscripcionTypologyDto genre) {
            this.genre = genre;
        }

        public AdmPreinscripcionTypologyDto getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(AdmPreinscripcionTypologyDto maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public AdmPreinscripcionTypologyDto getMayanPeople() {
            return mayanPeople;
        }

        public void setMayanPeople(AdmPreinscripcionTypologyDto mayanPeople) {
            this.mayanPeople = mayanPeople;
        }

        public AdmPreinscripcionTypologyDto getProfession() {
            return profession;
        }

        public void setProfession(AdmPreinscripcionTypologyDto profession) {
            this.profession = profession;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public AdmPreinscripcionAddressDto getAddress() {
            return address;
        }

        public void setAddress(AdmPreinscripcionAddressDto address) {
            this.address = address;
        }

        public List<AdmPreinscripcionPhoneDto> getPhones() {
            return phones;
        }

        public void setPhones(List<AdmPreinscripcionPhoneDto> phones) {
            this.phones = phones;
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

        public AdmPreinscripcionTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmPreinscripcionTypologyDto status) {
            this.status = status;
        }

        public String getMembershipNumber() {
            return membershipNumber;
        }

        public void setMembershipNumber(String membershipNumber) {
            this.membershipNumber = membershipNumber;
        }

        public LocalDateTime getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(LocalDateTime dateCreated) {
            this.dateCreated = dateCreated;
        }

        public AdmPreinscripcionTypologyDto getLinguisticCommunity() {
            return linguisticCommunity;
        }

        public void setLinguisticCommunity(AdmPreinscripcionTypologyDto linguisticCommunity) {
            this.linguisticCommunity = linguisticCommunity;
        }

        public String getNit() {
            return nit;
        }

        public void setNit(String nit) {
            this.nit = nit;
        }
    }

    //Person address
    @Schema(name = "AddressPreinscripcionDto")
    @JsonbPropertyOrder({"personFullAddress", "personZoneId", "personVillageId"})
    public static class AdmPreinscripcionAddressDto {
        private Long addressId;
        private String addressLine;
        private AdmPreinscripcionTypologyDto state;
        private AdmPreinscripcionTypologyDto city;
        private AdmPreinscripcionTypologyDto zone;
        private AdmPreinscripcionTypologyDto village;
        private AdmPreinscripcionTypologyDto type;

        public AdmPreinscripcionAddressDto() {
        }

        public AdmPreinscripcionAddressDto(Long addressId, String addressLine, AdmPreinscripcionTypologyDto state, AdmPreinscripcionTypologyDto city, AdmPreinscripcionTypologyDto zone, AdmPreinscripcionTypologyDto village, AdmPreinscripcionTypologyDto type) {
            this.addressId = addressId;
            this.addressLine = addressLine;
            this.state = state;
            this.city = city;
            this.zone = zone;
            this.village = village;
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

        public AdmPreinscripcionTypologyDto getState() {
            return state;
        }

        public void setState(AdmPreinscripcionTypologyDto state) {
            this.state = state;
        }

        public AdmPreinscripcionTypologyDto getCity() {
            return city;
        }

        public void setCity(AdmPreinscripcionTypologyDto city) {
            this.city = city;
        }

        public AdmPreinscripcionTypologyDto getZone() {
            return zone;
        }

        public void setZone(AdmPreinscripcionTypologyDto zone) {
            this.zone = zone;
        }

        public AdmPreinscripcionTypologyDto getVillage() {
            return village;
        }

        public void setVillage(AdmPreinscripcionTypologyDto village) {
            this.village = village;
        }

        public AdmPreinscripcionTypologyDto getType() {
            return type;
        }

        public void setType(AdmPreinscripcionTypologyDto type) {
            this.type = type;
        }
    }

    public static class AdmPreinscripcionPhoneDto {
        private Long phoneId;
        private Long phone;
        private AdmPreinscripcionTypologyDto type;
        private AdmPreinscripcionTypologyDto status;

        public AdmPreinscripcionPhoneDto() {
        }

        public AdmPreinscripcionPhoneDto(Long phone) {
            this.phone = phone;
        }

        public AdmPreinscripcionPhoneDto(Long phoneId, Long phone, AdmPreinscripcionTypologyDto type, AdmPreinscripcionTypologyDto status) {
            this.phoneId = phoneId;
            this.phone = phone;
            this.type = type;
            this.status = status;
        }

        public AdmPreinscripcionTypologyDto getStatus() {
            return status;
        }

        public void setStatus(AdmPreinscripcionTypologyDto status) {
            this.status = status;
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

        public AdmPreinscripcionTypologyDto getType() {
            return type;
        }

        public void setType(AdmPreinscripcionTypologyDto type) {
            this.type = type;
        }
    }

    //Beneficiary
    @Schema(name = "BeneficiaryPreinscripcionDto")
    @JsonbPropertyOrder({"beneficiary", "kinship"})
    public static class AdmPreinscripcionBeneficiaryDto {
        private AdmPreinscripcionPersonDto person;
        private AdmPreinscripcionTypologyDto kinship;

        public AdmPreinscripcionBeneficiaryDto() {
        }

        public AdmPreinscripcionBeneficiaryDto(AdmPreinscripcionPersonDto person, AdmPreinscripcionTypologyDto kinship) {
            this.person = person;
            this.kinship = kinship;
        }


        public AdmPreinscripcionPersonDto getPerson() {
            return person;
        }

        public void setPerson(AdmPreinscripcionPersonDto person) {
            this.person = person;
        }

        public AdmPreinscripcionTypologyDto getKinship() {
            return kinship;
        }

        public void setKinship(AdmPreinscripcionTypologyDto kinship) {
            this.kinship = kinship;
        }
    }

    public static class AdmPreinscripcionTypologyDto {
        private Long typologyId;
        private String description;

        public AdmPreinscripcionTypologyDto() {
        }

        public AdmPreinscripcionTypologyDto(Long typologyId) {
            this.typologyId = typologyId;
        }

        public AdmPreinscripcionTypologyDto(Long typologyId, String description) {
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

    public static class AdmPreinscripcionOrganizationDto implements Serializable {
            private UUID organizationKey;
            private String organizationName;
            private Double entryContribution;
            private Double entryFee;

        public AdmPreinscripcionOrganizationDto() {
        }

        public AdmPreinscripcionOrganizationDto(UUID organizationKey, String organizationName) {
            this.organizationKey = organizationKey;
            this.organizationName = organizationName;
        }

        public AdmPreinscripcionOrganizationDto(UUID organizationKey, String organizationName, Double entryContribution, Double entryFee) {
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


}
