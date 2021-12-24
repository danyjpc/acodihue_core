package com.peopleapps.dto.inputs.associate;

import com.peopleapps.dto.inputs.preinscripcionDto.AdmPreinscripcionDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "AdmAssociatePhoneDto")
public class AdmAssociatePhoneDto {

    private Long phoneId;
    private Long phone;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type;
    private AdmAssociateCreatedByDto createdBy;
    private Boolean leader;
    private AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status;
    public AdmAssociatePhoneDto() {
    }

    public AdmAssociatePhoneDto(Long phoneId, Long phone, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto type, AdmAssociateCreatedByDto createdBy, Boolean leader, AdmPreinscripcionDto.AdmPreinscripcionTypologyDto status) {
        this.phoneId = phoneId;
        this.phone = phone;
        this.type = type;
        this.createdBy = createdBy;
        this.leader = leader;
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
