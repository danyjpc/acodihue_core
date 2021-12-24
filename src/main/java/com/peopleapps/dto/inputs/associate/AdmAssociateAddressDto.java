package com.peopleapps.dto.inputs.associate;

import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "AdmAssociateAddressDto")
public class AdmAssociateAddressDto {

    private Long addressId;
    private String addressLine;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto state;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto city;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto zone;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto village;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type;
    private AdmAssociateCreatedByDto createdBy;
    private Boolean leader;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status;
    public AdmAssociateAddressDto() {
    }

    public AdmAssociateAddressDto(Long addressId, String addressLine, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto state, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto city, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto zone, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto village, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type, AdmAssociateCreatedByDto createdBy, Boolean leader, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status) {
        this.addressId = addressId;
        this.addressLine = addressLine;
        this.state = state;
        this.city = city;
        this.zone = zone;
        this.village = village;
        this.type = type;
        this.createdBy = createdBy;
        this.leader = leader;
        this.status = status;
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

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getState() {
        return state;
    }

    public void setState(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto state) {
        this.state = state;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getCity() {
        return city;
    }

    public void setCity(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto city) {
        this.city = city;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getZone() {
        return zone;
    }

    public void setZone(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto zone) {
        this.zone = zone;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getVillage() {
        return village;
    }

    public void setVillage(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto village) {
        this.village = village;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getType() {
        return type;
    }

    public void setType(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type) {
        this.type = type;
    }

    public AdmAssociateCreatedByDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdmAssociateCreatedByDto createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public AdmPreinscripcionDto.AdmPreinscripcionTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status) {
        this.status = status;
    }

    public static class AdmAssociateCreatedByDto{
        private UUID personKey;

        public AdmAssociateCreatedByDto() {
        }

        public AdmAssociateCreatedByDto(UUID personKey) {
            this.personKey = personKey;
        }

        public UUID getPersonKey() {
            return personKey;
        }

        public void setPersonKey(UUID personKey) {
            this.personKey = personKey;
        }
    }
}
