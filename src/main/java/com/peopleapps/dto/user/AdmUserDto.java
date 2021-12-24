package com.peopleapps.dto.user;

import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.model.AdmTypology;
import com.peopleapps.util.CsnConstants;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.time.LocalDateTime;

@JsonbPropertyOrder({"userId",
        "person",
        "password",
        "dateCreated",
        "role",
        "status"})
public class AdmUserDto {


    private Long userId;
    private AdmPersonDto person;
    private String password;
    @JsonbDateFormat(value = CsnConstants.DATE_FORMAT)
    private LocalDateTime dateCreated;
    private AdmTypologyDto role;
    private AdmTypologyDto status;

    public AdmUserDto() {
    }

    public AdmUserDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AdmPersonDto getPerson() {
        return person;
    }

    public void setPerson(AdmPersonDto person) {
        this.person = person;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AdmTypologyDto getRole() {
        return role;
    }

    public void setRole(AdmTypologyDto role) {
        this.role = role;
    }

    public AdmTypologyDto getStatus() {
        return status;
    }

    public void setStatus(AdmTypologyDto status) {
        this.status = status;
    }
}
